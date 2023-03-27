package com.invoice.constratista.datasource.database.event.budget

import com.invoice.constratista.datasource.database.inventory.PriceEntity
import com.invoice.constratista.datasource.database.inventory.ProductEntity
import jakarta.persistence.*
import lombok.Data
import java.sql.Date

@Entity(name = "reserved")
@Table
@Data
data class ReservedEntity(
    @Id val id: String,
    @Column(name = "date_expiry") val dateExpiry: Date,
) {
    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "price_id")
    var price: PriceEntity? = null
}
