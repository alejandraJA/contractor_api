package com.invoice.constratista.datasource.database.entity.inventory

import com.invoice.constratista.datasource.database.entity.UserEntity
import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset

@Entity(name = "[product_inventory]")
@Table
@Data
data class ProductInventoryEntity(
    @Id val id: String,
    val quantity: Int,
    val modified: DateTimeOffset,
) {
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null

    @OrderBy("date")
    @OneToMany(mappedBy = "productInventoryEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    var costEntities: MutableList<CostEntity> = mutableListOf()

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    open var user: UserEntity? = null
}