package com.invoice.constratista.datasource.database.entity.inventory

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Data

@Entity(name = "[product_base]")
@Table
@Data
data class ProductBaseEntity(
    @Id val id: String,
    val price: Double,
    val description: String,
    val taxability: Boolean,
    val unitKey: String,
    val unitName: String,
    val sku: String,
)
