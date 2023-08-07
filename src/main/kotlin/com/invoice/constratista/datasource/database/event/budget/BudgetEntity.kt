package com.invoice.constratista.datasource.database.event.budget

import com.fasterxml.jackson.annotation.JsonIgnore
import com.invoice.constratista.datasource.database.event.EventEntity
import jakarta.persistence.*
import lombok.Data
import java.sql.Date

@Entity(name = "budget")
@Table
@Data
data class BudgetEntity(
    @Id val id: String,
    val number: Int,
    val date: Date,
    val conditions: String,
    val status: String,
) {
    @OrderBy("number")
    @OneToMany(mappedBy = "budget", cascade = [CascadeType.ALL], orphanRemoval = true)
    var partEntities: MutableList<PartEntity> = mutableListOf()

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    var event: EventEntity? = null
}