package com.invoice.constratista.datasource.database

import com.fasterxml.jackson.annotation.JsonIgnore
import com.invoice.constratista.datasource.database.event.EventEntity
import com.invoice.constratista.datasource.database.inventory.ProductInventoryEntity
import jakarta.persistence.*
import lombok.Data
import org.hibernate.Hibernate
import java.sql.Date

@Entity(name = "user")
@Table
@Data
data class UserEntity(
    @Id
    @GeneratedValue
    val id: Long,
    val username: String,
    @JsonIgnore
    var password: String,
    val registration: Date,
    @JsonIgnore
    var token: String
) {
    @OrderBy("date")
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var eventEntities: MutableList<EventEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var productInventoryEntities: MutableList<ProductInventoryEntity> = mutableListOf()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , username = $username , password = $password , registration = $registration , token = $token )"
    }

    @OneToMany(mappedBy = "userEntity", orphanRemoval = true)
    var customerEntities: MutableList<CustomerEntity> = mutableListOf()

}