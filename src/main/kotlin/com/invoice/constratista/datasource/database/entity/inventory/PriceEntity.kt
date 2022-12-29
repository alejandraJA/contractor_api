package com.invoice.constratista.datasource.database.entity.inventory

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset
import org.hibernate.Hibernate

@Entity(name = "[price]")
@Table
@Data
data class PriceEntity(
    @Id val id: String,
    @Column(name = "unit_price") val unitPrice: Double,
    var date: DateTimeOffset?,
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id")
    @JsonIgnore
    var product: ProductEntity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PriceEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , unitPrice = $unitPrice , date = $date , product = $product )"
    }
}
