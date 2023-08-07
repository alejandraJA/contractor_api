package com.invoice.constratista.datasource.database.inventory

import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset
import org.hibernate.Hibernate
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

    @OrderBy("date")
    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL],)
    var priceEntities: MutableList<PriceEntity> = mutableListOf()

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL],)
    var taxEntities: MutableList<TaxEntity> = mutableListOf()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ProductEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , modified = $modified , productBase = $productBase )"
    }
}