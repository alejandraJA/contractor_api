package com.invoice.constratista.datasource.database


import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Data

@Entity(name = "address")
@Table
@Data
data class AddressEntity(
    @Id val id: String,
    var street: String,
    var exterior: String,
    var interior: String,
    var neighborhood: String,
    var city: String,
    var municipality: String,
    var zip: String,
    var state: String,
    var country: String,
)