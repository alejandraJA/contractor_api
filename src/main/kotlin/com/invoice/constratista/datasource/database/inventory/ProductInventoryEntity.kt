package com.invoice.constratista.datasource.database.inventory

import com.fasterxml.jackson.annotation.JsonIgnore
import com.invoice.constratista.datasource.database.UserEntity
import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset
import org.hibernate.Hibernate

@Entity(name = "product_inventory")
@Table
@Data
data class ProductInventoryEntity(
    @Id val id: String,
    val quantity: Int,
    var modified: DateTimeOffset?,
) {
    @OneToOne()
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null

    @OrderBy("date")
    @OneToMany(mappedBy = "productInventoryEntity", cascade = [CascadeType.ALL])
    var costEntities: MutableList<CostEntity> = mutableListOf()

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    @JsonIgnore
    var user: UserEntity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ProductInventoryEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , quantity = $quantity , modified = $modified , product = $product , user = $user )"
    }
}