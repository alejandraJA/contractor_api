package com.invoice.constratista.controller.authentication

import com.invoice.constratista.controller.authentication.request.SingRequest
import com.invoice.constratista.controller.authentication.request.UpdateTokenRequest
import com.invoice.constratista.datasource.database.entity.UserEntity
import com.invoice.constratista.datasource.database.repository.UserRepository
import com.invoice.constratista.service.MyUserDetailService
import com.invoice.constratista.utils.Response
import com.invoice.constratista.utils.TokenUtils
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["sing"])
@RestController
class AuthenticationController {
    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var tokenUtil: TokenUtils

    @Autowired
    private lateinit var userDetailsService: MyUserDetailService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    @RequestMapping(value = ["/in"], method = [RequestMethod.POST])
    fun singIn(@RequestBody request: SingRequest): ResponseEntity<Any> {
        var status = false
        var message = "${request.username} does not exist."
        var data: Any? = null
        val userEntity: UserEntity? = repository.findUserEntityByUsername(request.username?:"")
        if (userEntity != null) {
            if (passwordEncoder.matches(request.password, userEntity.password)) {
                val user = User(userEntity.username, userEntity.password, ArrayList())
                status = true
                message = "Authenticated."
                data = tokenUtil.getExpiration(tokenUtil.generateToken(user))
            } else message = "Password incorrect. Please check this."
        }
        return ResponseEntity.ok(Response(status, message, data))
    }

    @RequestMapping(value = ["/up"], method = [RequestMethod.POST])
    fun singUp(@RequestBody request: SingRequest): ResponseEntity<Any> {
        val user: UserEntity? = userDetailsService.save(request)
        return ResponseEntity.ok(
            Response(
                status = user != null,
                message = if (user == null) "User already exist." else "Registered user",
                data = user
            )
        )
    }

    @RequestMapping(value = ["/updateToken"], method = [RequestMethod.PUT])
    fun validateToken(@RequestBody request: UpdateTokenRequest): ResponseEntity<Any> {
        var status = false
        var message: String
        var data: Any? = null
        try {
            if (tokenUtil.isValid(request.token, userDetailsService.loadUserByUsername(request.username))) {
                status = true
                message = "Token available"
                data = tokenUtil.getExpiration(request.token)
            } else message = "The token does not correspond to the user."
        } catch (_: ExpiredJwtException) {
            val user = repository.findUserEntityByUsername(request.username)!!
            if (user.token == request.token) {
                status = true
                message = "Token refresh."
                data = tokenUtil.getExpiration(tokenUtil.generateToken(User(user.username, user.password, ArrayList())))
            } else message = "The token does not correspond to the user."
        } catch (_: NullPointerException) {
            message = "${request.username} does not exist in database."
        }
        catch (_: SignatureException) {
            message = "Token invalid"
        }

        return ResponseEntity.ok(Response(status, message, data))
    }

}