package com.invoice.constratista.security

import com.invoice.constratista.service.MyUserDetailService
import com.invoice.constratista.utils.JwtTokenUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component

@Component
class JwtUtil {

    @Autowired
    private lateinit var userDetailService: MyUserDetailService

    @Autowired
    private lateinit var tokenUtil: JwtTokenUtil
    fun addAuthentication(response: HttpServletResponse, username: String) {
        val token = tokenUtil.generateToken(userDetailService.loadUserByUsername(username))
        response.addHeader("Authorization", token)
    }

    fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader("Authorization")

        return if (token.isNotEmpty()) {
            val username = tokenUtil.getAllClaimsFromToken(token).subject
            if (username.isNotEmpty()) UsernamePasswordAuthenticationToken(
                username,
                null,
                AuthorityUtils.NO_AUTHORITIES
            )
            else null
        } else null
    }
}