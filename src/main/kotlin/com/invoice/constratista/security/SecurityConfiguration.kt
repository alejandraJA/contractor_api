package com.invoice.constratista.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.sql.DataSource


@Configuration
class SecurityConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    // Tal vez me deshaga de estas
    @Bean
    fun configure(
        http: HttpSecurity,
        jwtFilter: JwtFilter,
    ): SecurityFilterChain {
        http
            .csrf().disable()
//            .authorizeHttpRequests()
//            .requestMatchers("/singIn", "/singUp").permitAll()
//            .anyRequest().authenticated()
//            .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/singIn", "/singUp").permitAll() // Accesos permitidos
                    .anyRequest().authenticated() // El resto de las peticiones requieren autentificación
                    .and()
                    .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            }
        return http.build()
    }

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    fun configAuthentication(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("SELECT username, password FROM [dbo].[user] WHERE username =?")
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers("/singIn", "singUp").anyRequest()
        }
    }

}