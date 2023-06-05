package com.invoice.constratista.datasource.mapper

import com.invoice.constratista.controller.event.Budget
import com.invoice.constratista.controller.event.BudgetWithParts
import com.invoice.constratista.controller.event.EventWithBudgets
import com.invoice.constratista.controller.event.Reserved
import com.invoice.constratista.datasource.database.AddressEntity
import com.invoice.constratista.datasource.database.DateEntity
import com.invoice.constratista.datasource.database.event.EventEntity
import com.invoice.constratista.datasource.database.event.NoteEntity
import com.invoice.constratista.datasource.database.event.ScheduleEntity
import com.invoice.constratista.datasource.database.event.budget.BudgetEntity
import com.invoice.constratista.datasource.database.event.budget.PartEntity
import com.invoice.constratista.datasource.database.event.budget.ReservedEntity
import com.invoice.constratista.datasource.database.inventory.PriceRepository
import com.invoice.constratista.datasource.repository.sql.ProductRepository
import com.invoice.constratista.utils.getDateTimeOffet
import com.invoice.constratista.utils.toDate
import java.util.*
import java.sql.Date as SqlDate

object Mapper {
    fun EventWithBudgets.toEventEntity(
        productRepository: ProductRepository,
        priceRepository: PriceRepository
    ): EventEntity {
        val eventEntity = EventEntity(
            event.id,
            event.state,
            event.note,
            event.eventName,
            getDateTimeOffet()!!
        )
        val budgetEntities = mutableListOf<BudgetEntity>()
        val noteEntities: MutableList<NoteEntity> = mutableListOf()
        val scheduleEntities: MutableList<ScheduleEntity> = mutableListOf()
        val dateEntities: MutableList<DateEntity> = mutableListOf()

        budgets.forEach {
            budgetEntities.add(
                it.toBudgetEntity(
                    eventEntity,
                    productRepository,
                    priceRepository
                )
            )
        }
        notes.forEach {
            val note = NoteEntity(it.id, it.note)
            note.event = eventEntity
            noteEntities.add(note)
        }
        schedules.forEach {
            val scheduleEntity =
                ScheduleEntity(it.schedule.id, it.schedule.date, it.schedule.state, it.schedule.note, it.schedule.name)
            scheduleEntity.event = eventEntity
            scheduleEntity.address = AddressEntity(
                it.address.id,
                it.address.street,
                it.address.exterior,
                it.address.interior,
                it.address.neighborhood,
                it.address.city,
                it.address.municipality,
                it.address.zip,
                it.address.state,
                it.address.country
            )
            scheduleEntities.add(scheduleEntity)
        }
        dates.forEach {
            dateEntities.add(DateEntity(it.id, it.idReference, it.date, it.name))
        }

        eventEntity.budgetEntities.addAll(budgetEntities)
        eventEntity.noteEntities.addAll(noteEntities)
        eventEntity.scheduleEntities.addAll(scheduleEntities)
        eventEntity.dateEntities.addAll(dateEntities)
        return eventEntity
    }

    private fun BudgetWithParts.toBudgetEntity(
        eventEntity: EventEntity,
        productRepository: ProductRepository,
        priceRepository: PriceRepository
    ): BudgetEntity {
        val budgetEntity = budget.toBudgetEntity()
        parts.forEach {
            val partEntity = PartEntity(it.part.id, it.part.number, it.part.quantity, it.part.discount)
            partEntity.budget = budgetEntity
            partEntity.reserved = it.reserved.toReservedEntity(
                productRepository,
                priceRepository
            )
        }
        budgetEntity.event = eventEntity
        return budgetEntity
    }

    fun Reserved.toReservedEntity(
        productRepository: ProductRepository,
        priceRepository: PriceRepository
    ): ReservedEntity {
        val reserved: ReservedEntity
        var date: Date?
        date = if (dateExpiry!!.isNotEmpty()) dateExpiry.toDate()
        else Date()
        if (date == null) date = Date()
        reserved = ReservedEntity(id, SqlDate(date.time))
        reserved.product = productRepository.findById(this.idProduct).get()
        reserved.price = priceRepository.findById(this.idPrice).get()
        return reserved
    }

    private fun Budget.toBudgetEntity() =
        BudgetEntity(id, number, date, conditions, status)

}

