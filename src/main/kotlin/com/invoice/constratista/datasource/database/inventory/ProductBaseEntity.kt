package com.invoice.constratista.datasource.database.inventory

import jakarta.persistence.*
import lombok.Data

@Entity(name = "product_base")
@Table
@Data
data class ProductBaseEntity(
    @Id val id: String,
    val description: String,
    @Column(name = "tax_included") val taxIncluded: Boolean,
    val taxability: String,
    val unitKey: String,
    val unitName: String,
    val sku: String,
    @Column(name = "product_key") val productKey: String,
) {

    @OrderBy("date")
    @OneToMany(mappedBy = "productBaseEntity", orphanRemoval = true)
    var priceEntities: MutableList<PriceEntity> = mutableListOf()

    @OrderBy("date")
    @OneToMany(mappedBy = "productBaseEntity", orphanRemoval = true)
    var costEntities: MutableList<CostEntity> = mutableListOf()
}
