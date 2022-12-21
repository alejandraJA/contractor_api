package com.invoice.constratista.datasource.database.entity.inventory

import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset

@Entity(name = "[price]")
@Table
@Data
data class PriceEntity(
    @Id val id: String,
    @Column(name = "unit_price") val unitPrice: Double,
    val date: DateTimeOffset,
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null
}
