package com.invoice.constratista.controller

import com.invoice.constratista.controller.request.SingRequest
import com.invoice.constratista.datasource.database.entity.UserEntity
import com.invoice.constratista.datasource.database.repository.UserRepository
import com.invoice.constratista.service.MyUserDetailService
import com.invoice.constratista.utils.JwtTokenUtil
import com.invoice.constratista.utils.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


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

    @RequestMapping(value = ["singIn"], method = [RequestMethod.POST])
    fun singIn(@RequestBody singRequest: SingRequest): ResponseEntity<Any> {
        val user = userRepository.findUserEntityByUsername(singRequest.username)
        val userStatus = passwordEncoder.matches(singRequest.password, user.password)
        return if (userStatus) {
            val userDetail = userDetailsService.loadUserByUsername(singRequest.username)
            ResponseEntity.ok(
                Response(
                    status = true,
                    message = "Authentication ok",
                    data = jwtTokenUtil.generateToken(userDetail)
                )
            )
        } else ResponseEntity.ok(Response(status = false, message = "Incorrect Password or Email.", data = ""))
    }

    @RequestMapping(value = ["singUp"], method = [RequestMethod.POST])
    fun singUp(@RequestBody singRequest: SingRequest): ResponseEntity<Any> {
        val user: UserEntity? = userDetailsService.save(singRequest)
        return ResponseEntity.ok(
            Response(
                status = user != null,
                message = if (user == null) "User already exist." else "Registered user",
                data = user
            )
        )
    }
    @RequestMapping(value = ["validateToken"], method = [RequestMethod.GET])
    fun validateToken(): ResponseEntity<Any> {
        val validateToken = jwtTokenUtil.validateToken(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbGVqYW5kcmFKaW1lbmV6QXZhbG9zQCIsI" +
                    "mV4cCI6MTY3MDk5MTIyMSwiaWF0IjoxNjcwOTczMjIxfQ.UBi-XYpjVU5ZtjSggupgeKz" +
                    "V2ps1jsgW2ylFahrBQwCWnoTbgKYMu1gzgSyeqTMITIz6iiSfk-mIBLvX6Pl3GQ",
            userDetailsService.loadUserByUsername("AlejandraJimenezAvalos@")
        )
        return ResponseEntity.ok(validateToken)
    }

}