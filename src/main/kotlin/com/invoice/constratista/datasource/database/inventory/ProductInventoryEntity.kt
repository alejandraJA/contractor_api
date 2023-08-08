package com.invoice.constratista.datasource.database.inventory

import com.fasterxml.jackson.annotation.JsonIgnore
import com.invoice.constratista.datasource.database.UserEntity
import jakarta.persistence.*
import lombok.Data
import java.sql.Date


@Entity(name = "product_inventory")
@Table
@Data
data class ProductInventoryEntity(
    @Id val id: String,
    val quantity: Int,
    var modified: Date?,
) {
    @OneToOne()
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    @JsonIgnore
    var user: UserEntity? = null

}

