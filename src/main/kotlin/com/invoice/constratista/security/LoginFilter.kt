package com.invoice.constratista.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.invoice.constratista.controller.request.SingRequest
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import java.util.*

class LoginFilter(
    path: String,
    authenticationManager: AuthenticationManager
) : AbstractAuthenticationProcessingFilter(path, authenticationManager) {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val body = request.inputStream
        val user: SingRequest = ObjectMapper().readValue(body, SingRequest::class.java)
        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                user.username,
                user.password,
                Collections.emptyList()
            )
        )
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val clientOrigin = request.getHeader("origin")

        response.addHeader("Access-Control-Allow-Origin", clientOrigin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,  DELETE, PUT");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type, Origin, Authorization, X-Auth-Token");
        JwtUtil().addAuthentication(response, authResult.name);
    }
}