package com.invoice.constratista.datasource.database

import com.fasterxml.jackson.annotation.JsonIgnore
import com.invoice.constratista.datasource.database.event.EventEntity
import jakarta.persistence.*
import lombok.Data

@Entity(name = "customer")
@Table
@Data
data class CustomerEntity(
    @Id val id: String,
    @Column(name = "created_at")
    val createdAt: String,
    @Column(name = "email")
    val email: String?,
    @Column(name = "legal_name")
    val legalName: String,
    @Column(name = "livemode")
    val liveMode: Boolean,
    @Column(name = "organization")
    val organization: String,
    @Column(name = "phone")
    val phone: String?,
    @Column(name = "tax_id")
    val taxId: String,
    @Column(name = "tax_system")
    val taxSystem: String
) {
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "address_entity_id")
    var addressEntity: AddressEntity? = null

    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    @JsonIgnore
    var userEntity: UserEntity? = null

    @OneToMany(mappedBy = "customerEntity", orphanRemoval = true)
    @JsonIgnore
    var eventEntities: MutableList<EventEntity> = mutableListOf()
}
