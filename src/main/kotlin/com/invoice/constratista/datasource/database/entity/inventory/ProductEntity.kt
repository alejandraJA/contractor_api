package com.invoice.constratista.datasource.database.entity.inventory

import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset

@Entity(name = "[product]")
@Table
@Data
data class ProductEntity(
    @Id val id: String,
    val name: String,
    val modified: DateTimeOffset
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_base_id")
    var productBase: ProductBaseEntity? = null

    @OrderBy("date")
    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    var priceEntities: MutableList<PriceEntity> = mutableListOf()

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    var taxEntities: MutableList<TaxEntity> = mutableListOf()
}