package com.invoice.constratista.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfiguration {
//    @Bean
//    fun bearerTokenResolver(): BearerTokenResolver {
//        val bearerTokenResolver = DefaultBearerTokenResolver()
//        bearerTokenResolver.setBearerTokenHeaderName(HttpHeaders.PROXY_AUTHORIZATION)
//        return bearerTokenResolver
//    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun configure(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.csrf().disable()
        return httpSecurity.build()
    }

}