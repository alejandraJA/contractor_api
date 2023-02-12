package com.invoice.constratista.utils

import com.invoice.constratista.controller.auth.response.TokenResponse
import com.invoice.constratista.datasource.repository.sql.UserRepository
import io.jsonwebtoken.Claims
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
class TokenUtils : Serializable {
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Autowired
    private lateinit var repository: UserRepository

    fun getUsername(token: String): String? = token.getClaim { claim: Claims -> claim.subject }
    fun getExpiration(token: String) = token.getExpirationDate()?.let { TokenResponse(token, it) }
    fun isExpired(token: String) = token.getExpirationDate()?.before(Date())
    fun generateToken(user: UserDetails): String = user.username.doGenerateToken(HashMap())
    fun isValid(token: String, user: UserDetails) = (getUsername(token) == user.username) && !isExpired(token)!!
    private fun String.getExpirationDate(): Date? = this.getClaim { claim -> claim.expiration }
    private fun <T> String.getClaim(claims: Function<Claims, T>): T = claims.apply(this.getAllClaims()!!)
    private fun String.getAllClaims(): Claims? = Jwts.parser().setSigningKey(secret).parseClaimsJws(this).body
    private fun String.doGenerateToken(claims: Map<String, Any>): String {
        val token = Jwts.builder().setClaims(claims).setSubject(this).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + Constants.JWT_TOKEN_VALIDITY * 100))
            .signWith(SignatureAlgorithm.HS512, secret).compact()
        val user = repository.findUserEntityByUsername(username = this)!!
        user.token = token
        repository.save(user)
        return token
    }

}