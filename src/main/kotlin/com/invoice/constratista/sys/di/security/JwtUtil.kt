package com.invoice.constratista.sys.di.security

import com.invoice.constratista.sys.service.MyUserDetailService
import com.invoice.constratista.utils.TokenUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component

@Component
class JwtUtil {

    @Autowired
    private lateinit var userDetailService: MyUserDetailService

    @Autowired
    private lateinit var tokenUtil: TokenUtils

    fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader("Authorization")

        return if (token.isNotEmpty()) {
            val username = tokenUtil.getUsername(token)!!
            val user = userDetailService.loadUserByUsername(username)
            if (username.isNotEmpty()) UsernamePasswordAuthenticationToken(
                username,
                user.password,
                AuthorityUtils.NO_AUTHORITIES
            )
            else null
        } else null
    }
}