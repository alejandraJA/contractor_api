package com.invoice.constratista.datasource.database.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.invoice.constratista.datasource.database.entity.event.EventEntity
import com.invoice.constratista.datasource.database.entity.inventory.ProductInventoryEntity
import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset

@Entity(name = "[user]")
@Table
@Data
data class UserEntity(
    @Id
    @GeneratedValue
    val id: Long,
    val username: String,
    @JsonIgnore
    var password: String,
    val registration: DateTimeOffset,
    @JsonIgnore
    var token: String
) {
    @OrderBy("date")
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var eventEntities: MutableList<EventEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var productInventoryEntities: MutableList<ProductInventoryEntity> = mutableListOf()

}