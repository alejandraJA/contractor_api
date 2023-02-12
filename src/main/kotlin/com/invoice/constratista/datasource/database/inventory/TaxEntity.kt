package com.invoice.constratista.datasource.database.inventory

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.Data
import org.hibernate.Hibernate

@Suppress("SENSELESS_COMPARISON")
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
    @JsonIgnore
    var product: ProductEntity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as TaxEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , type = $type , rate = $rate , factor = $factor , withholding = $withholding , localTax = $localTax , product = $product )"
    }
}
