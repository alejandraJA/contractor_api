package com.invoice.constratista.utils

import com.invoice.constratista.datasource.database.repository.UserRepository
import com.invoice.constratista.utils.Constants.JWT_TOKEN_VALIDITY
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*
import java.util.function.Function


@Component
class JwtTokenUtil : Serializable {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Autowired
    private lateinit var userRepository: UserRepository

    //retrieve username from jwt token
    @Throws(ExpiredJwtException::class)
    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    /**
     * Retrieve expiration date from jwt token
     */
    @Throws(ExpiredJwtException::class)
    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(
            token
        ) { obj: Claims -> obj.expiration }
    }

    @Throws(ExpiredJwtException::class)
    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    //for retrieveing any information from token we will need the secret key
    @Throws(ExpiredJwtException::class)
    fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    //check if the token has expired
    @Throws(ExpiredJwtException::class)
    fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    //generate token for user
    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        val token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact()
        val userEntity = userRepository.findUserEntityByUsername(subject)!!
        userEntity.token = token
        userRepository.save(userEntity)
        return token
    }

    //validate token
    @Throws(ExpiredJwtException::class)
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }
}