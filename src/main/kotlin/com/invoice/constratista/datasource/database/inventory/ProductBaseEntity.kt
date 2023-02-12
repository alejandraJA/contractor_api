package com.invoice.constratista.datasource.database.inventory

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Data
import org.hibernate.Hibernate

@Entity(name = "[product_base]")
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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ProductBaseEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , description = $description , taxIncluded = $taxIncluded , taxability = $taxability , unitKey = $unitKey , unitName = $unitName , sku = $sku )"
    }
}
