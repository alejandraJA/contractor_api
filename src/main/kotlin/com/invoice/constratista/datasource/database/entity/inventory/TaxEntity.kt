package com.invoice.constratista.datasource.database.entity.inventory

import jakarta.persistence.*
import lombok.Data

@Entity(name = "[tax]")
@Table
@Data
data class TaxEntity(
    @Id val id: String,
    var type: String? = null,
    var rate: Double = 0.0,
    var factor: String? = null,
    var withholding: Boolean = false,
    var localTax: Boolean
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null
}
