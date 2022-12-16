package com.invoice.constratista.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class SecurityConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun configure(
        http: HttpSecurity,
        jwtFilter: JwtFilter,
    ): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/sing/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            }
        return http.build()
    }


}