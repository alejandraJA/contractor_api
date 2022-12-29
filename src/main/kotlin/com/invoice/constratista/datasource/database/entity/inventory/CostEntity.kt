package com.invoice.constratista.datasource.database.entity.inventory

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset
import org.hibernate.Hibernate

@Suppress("SENSELESS_COMPARISON")
@Entity(name = "[cost]")
@Table
@Data
data class CostEntity(
    @Id val id: String,
    @Column(name = "unit_cost") val unitCost: Double,
    val quantity: Int,
    var date: DateTimeOffset?,
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "vendor_id")
    var vendor: VendorEntity? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_inventory_id")
    @JsonIgnore
    var productInventoryEntity: ProductInventoryEntity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CostEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , unitCost = $unitCost , quantity = $quantity , date = $date , vendor = $vendor , productInventoryEntity = $productInventoryEntity )"
    }
}