package com.invoice.constratista.service

import com.invoice.constratista.controller.request.SingRequest
import com.invoice.constratista.datasource.database.entity.UserEntity
import com.invoice.constratista.datasource.database.repository.UserRepository
import microsoft.sql.DateTimeOffset
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*

@Service
class MyUserDetailService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepository.findUserEntityByUsername(username)
        return User(
            userEntity.username, userEntity.password,
            ArrayList()
        )
    }

    fun save(singRequest: SingRequest): UserEntity? {
        return if (!userRepository.existsUserEntityByUsername(singRequest.username)) {
            val date = Date()
            val userDetails = User(singRequest.username, singRequest.password, ArrayList())
            val calendar = Calendar.getInstance()
            val userEntity: UserEntity
            calendar.time = date
            calendar.add(Calendar.HOUR, 1)
            userEntity = UserEntity(
                0,
                singRequest.username,
                passwordEncoder.encode(singRequest.password),
                DateTimeOffset.valueOf(Timestamp(date.time), 1),
                ""
            )
            userRepository.save(userEntity)
        } else null
    }
}