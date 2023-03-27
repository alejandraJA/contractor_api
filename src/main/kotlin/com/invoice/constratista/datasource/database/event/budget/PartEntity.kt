package com.invoice.constratista.datasource.database.event.budget

import jakarta.persistence.*
import lombok.Data

@Entity(name = "part")
@Table
@Data
data class PartEntity(
    @Id val id: String,
    val number: Int,
    val quantity: Int,
    val discount: Double,
) {
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "reserved_id")
    var reserved: ReservedEntity? = null

    @ManyToOne
    @JoinColumn(name = "budget_id")
    var budget: BudgetEntity? = null
}