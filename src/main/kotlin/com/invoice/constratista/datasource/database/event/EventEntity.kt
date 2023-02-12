package com.invoice.constratista.datasource.database.event

import com.invoice.constratista.datasource.database.UserEntity
import com.invoice.constratista.datasource.database.event.budget.BudgetEntity
import jakarta.persistence.*
import lombok.Data
import microsoft.sql.DateTimeOffset

@Entity(name = "[event]")
@Table
@Data
data class EventEntity(
    @Id val id: String,
    val state: String,
    val note: String,
    @Column(name = "event_name") val eventName: String,
    val date: DateTimeOffset,
) {
    @OrderBy("number")
    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], orphanRemoval = true)
    var budgetEntities: MutableList<BudgetEntity> = mutableListOf()

    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], orphanRemoval = true)
    var noteEntities: MutableList<NoteEntity> = mutableListOf()

    @OrderBy("date")
    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], orphanRemoval = true)
    var scheduleEntities: MutableList<ScheduleEntity> = mutableListOf()

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
}