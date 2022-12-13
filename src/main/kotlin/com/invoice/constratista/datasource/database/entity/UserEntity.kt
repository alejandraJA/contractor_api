package com.invoice.constratista.datasource.database.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Data
import microsoft.sql.DateTimeOffset

@Entity(name = "[user]")
@Table
@Data
data class UserEntity(
    @Id
    @GeneratedValue
    val id: Long,
    val username: String,
    @JsonIgnore
    var password: String,
    val registration: DateTimeOffset
)