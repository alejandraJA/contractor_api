package com.invoice.constratista.datasource.mapper

import com.invoice.constratista.controller.event.Budget
import com.invoice.constratista.controller.event.BudgetWithParts
import com.invoice.constratista.controller.event.EventWithBudgets
import com.invoice.constratista.datasource.database.DateEntity
import com.invoice.constratista.datasource.database.event.EventEntity
import com.invoice.constratista.datasource.database.event.NoteEntity
import com.invoice.constratista.datasource.database.event.ScheduleEntity
import com.invoice.constratista.datasource.database.event.budget.BudgetEntity
import com.invoice.constratista.datasource.database.event.budget.PartEntity
import com.invoice.constratista.datasource.database.event.budget.ReservedEntity
import com.invoice.constratista.utils.getDateTimeOffet

object Mapper {
    fun EventWithBudgets.toEventEntity(): EventEntity {
        val eventEntity = EventEntity(
            event.id,
            event.state,
            event.note,
            event.eventName,
            getDateTimeOffet()
        )
        val budgetEntities = mutableListOf<BudgetEntity>()
        val noteEntities: MutableList<NoteEntity> = mutableListOf()
        val scheduleEntities: MutableList<ScheduleEntity> = mutableListOf()
        val dateEntities: MutableList<DateEntity> = mutableListOf()

        budgets.forEach {
            budgetEntities.add(it.toBudgetEntity())
        }


        eventEntity.budgetEntities.addAll(budgetEntities)
        eventEntity.noteEntities.addAll(noteEntities)
        eventEntity.scheduleEntities.addAll(scheduleEntities)
        eventEntity.dateEntities.addAll(dateEntities)
        return eventEntity
    }
}

private fun BudgetWithParts.toBudgetEntity(): BudgetEntity {
    val budgetEntity = budget.toBudgetEntity()
    parts.forEach {
        val partEntity = PartEntity(it.part.id, it.part.number, it.part.quantity, it.part.discount)
        partEntity.budget = budgetEntity
        partEntity.reserved = ReservedEntity(it.reserved)
    }
    return budgetEntity
}

private fun Budget.toBudgetEntity() =
    BudgetEntity(id, number, date, conditions, status)

