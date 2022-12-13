package com.invoice.constratista.datasource.database.repository;

import com.invoice.constratista.datasource.database.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
//    fun findUserEntityByUsernameAndAndPassword(email: String, password: String): UserEntity
    //fun findUserEntityByToken(token: String): UserEntity
    fun findUserEntityByUsername(username: String): UserEntity
    fun existsUserEntityByUsername(username: String): Boolean
    //fun existsUserEntityByUsernameAndPassword(username: String, password: String): Boolean
}