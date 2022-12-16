package com.invoice.constratista.security

import com.invoice.constratista.utils.Response
import com.nimbusds.jose.shaded.gson.Gson
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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
        val token: String? = (request as HttpServletRequest).getHeader("Authorization")
        (response as HttpServletResponse)
        val path = request.requestURI
        val auth = SecurityContextHolder.getContext()
        try {
            if (token != null) {
                val authentication = jwtUtil.getAuthentication(request)
                auth.authentication = authentication
                chain.doFilter(request, response)
                return
            } else {
                if (path.contains("/sing")) {
                    auth.authentication = UsernamePasswordAuthenticationToken(
                        "admin",
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                    )
                    chain.doFilter(request, response)
                    return
                }
            }
        } catch (e: ExpiredJwtException) {
            response.contentType = "application/json"
            response.writer.println(
                Gson().toJson(
                    Response(
                        status = false,
                        message = "Token expired.",
                        data = token
                    )
                )
            )
            response.writer.flush()
            return
        }
        response.sendError(401)
    }

}