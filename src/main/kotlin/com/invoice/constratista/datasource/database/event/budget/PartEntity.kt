package com.invoice.constratista.datasource.database.event.budget

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.Data

@Entity(name = "part")
@Table
@Data
data class PartEntity(
    @Id val id: String,
    val number: Int,
    var quantity: Int,
    var discount: Double,
) {
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "reserved_id")
    var reserved: ReservedEntity? = null

    @ManyToOne
    @JoinColumn(name = "budget_id")
    @JsonIgnore
    var budget: BudgetEntity? = null
}