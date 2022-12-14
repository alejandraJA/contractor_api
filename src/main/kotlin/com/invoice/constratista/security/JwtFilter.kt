package com.invoice.constratista.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.filter.GenericFilterBean

@Controller
class JwtFilter : GenericFilterBean() {

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        (request as HttpServletRequest).getHeader("Authorization")
        val path = request.requestURI
        val token: String? = request.getHeader("Authorization")
        if (token != null) {
            val authentication = jwtUtil.getAuthentication(request)
            SecurityContextHolder.getContext().authentication = authentication
            chain.doFilter(request, response)
        } else {
            if ((path == "/singIn") || (path == "/singUp")) {
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                    "admin",
                    null,
                    AuthorityUtils.NO_AUTHORITIES
                )
                chain.doFilter(request, response)
            }
        }
    }
}