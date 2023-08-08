package com.invoice.constratista.datasource.database.inventory

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.Data
import java.sql.Date

@Entity(name = "product")
@Table
@Data
data class ProductEntity(
    @Id val id: String,
    val name: String,
    var modified: Date?
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_base_id")
    var productBase: ProductBaseEntity? = null

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "price_entity_id")
    var priceEntity: PriceEntity? = null

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL],)
    var taxEntities: MutableList<TaxEntity> = mutableListOf()

    @OneToOne(cascade = [CascadeType.PERSIST], orphanRemoval = true)
    @JoinColumn(name = "cost_entity_id")
    var costEntity: CostEntity? = null

    @JsonIgnore
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "product_inventory_id")
    var productInventoryEntity: ProductInventoryEntity? = null
}