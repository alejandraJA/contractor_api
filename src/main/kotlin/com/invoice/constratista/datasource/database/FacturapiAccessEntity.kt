package com.invoice.constratista.datasource.database

import jakarta.persistence.*
import lombok.Data

@Entity(name = "[facturapi_access]")
@Table
@Data
data class FacturapiAccessEntity(
    @Id val id: String,
    val liveKey: String,
    val secretKey: String,
) {
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
}