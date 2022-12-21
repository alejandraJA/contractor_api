package com.invoice.constratista.datasource.database.entity.inventory

import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset

@Entity(name = "[cost]")
@Table
@Data
data class CostEntity(
    @Id val id: String,
    @Column(name = "unit_cost") val unitCost: Double,
    val quantity: Int,
    val date: DateTimeOffset,
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "vendor_id")
    var vendor: VendorEntity? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_inventory_id")
    var productInventoryEntity: ProductInventoryEntity? = null
}