package com.invoice.constratista.controller.authentication

import com.invoice.constratista.controller.authentication.request.SingRequest
import com.invoice.constratista.controller.authentication.request.UpdateTokenRequest
import com.invoice.constratista.controller.authentication.response.TokenResponse
import com.invoice.constratista.datasource.database.entity.UserEntity
import com.invoice.constratista.datasource.database.repository.UserRepository
import com.invoice.constratista.service.MyUserDetailService
import com.invoice.constratista.utils.JwtTokenUtil
import com.invoice.constratista.utils.Response
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["sing"])
@RestController
class AuthenticationController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var userDetailsService: MyUserDetailService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    @RequestMapping(value = ["/in"], method = [RequestMethod.POST])
    fun singIn(@RequestBody request: SingRequest): ResponseEntity<Any> {
        return if (userRepository.existsUserEntityByUsername(request.username)) {
            val user = userRepository.findUserEntityByUsername(request.username)
            val userStatus = passwordEncoder.matches(request.password, user!!.password)
            if (userStatus) {
                val userDetail = userDetailsService.loadUserByUsername(request.username)
                val token = jwtTokenUtil.generateToken(userDetail)
                ResponseEntity.ok(
                    Response(
                        status = true,
                        message = "Authenticated.",
                        data = TokenResponse(token, jwtTokenUtil.getExpirationDateFromToken(token))
                    )
                )
            } else ResponseEntity.ok(Response(status = false, message = "User or password incorrect.", data = ""))
        } else ResponseEntity.ok(
            Response(
                status = false,
                message = "${request.username} does not exist in database.",
                data = ""
            )
        )
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
        return try {
            val tokenStatus = jwtTokenUtil.validateToken(
                request.token,
                userDetailsService.loadUserByUsername(request.username)
            )
            if (tokenStatus) ResponseEntity.ok(
                Response(
                    status = true,
                    message = "Token available.",
                    data = TokenResponse(
                        request.token,
                        jwtTokenUtil.getExpirationDateFromToken(request.token)
                    )
                )
            )
            else ResponseEntity.ok(
                Response(
                    status = false,
                    message = "The token does not correspond to the user.",
                    data = null
                )
            )
        } catch (_: ExpiredJwtException) {
            val userEntity: UserEntity? = userRepository.findUserEntityByUsername(request.username)
            if ((userEntity != null) && (userEntity.token == request.token)) {
                val token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(userEntity.username))
                ResponseEntity.ok(
                    Response(
                        status = true,
                        message = "Token refresh.",
                        data = TokenResponse(token, jwtTokenUtil.getExpirationDateFromToken(token))
                    )
                )
            } else ResponseEntity.ok(
                Response(
                    status = false,
                    message = "The token does not correspond to the user.",
                    data = null
                )
            )
        } catch (_: NullPointerException) {
            ResponseEntity.ok(
                Response(
                    status = false,
                    message = "${request.username} does not exist in database.",
                    data = null
                )
            )
        }
    }

}