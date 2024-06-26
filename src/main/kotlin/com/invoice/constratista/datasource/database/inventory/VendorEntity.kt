package com.invoice.constratista.datasource.database.inventory

import com.invoice.constratista.datasource.database.AddressEntity
import jakarta.persistence.*
import lombok.Data

@Entity(name = "vendor")
@Table
@Data
data class VendorEntity(
    @Id val id: String,
    val name: String,
) {
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "address_id")
    var address: AddressEntity? = null
}