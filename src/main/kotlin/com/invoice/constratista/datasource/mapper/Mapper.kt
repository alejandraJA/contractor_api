package com.invoice.constratista.datasource.mapper

import com.invoice.constratista.controller.event.request.*
import com.invoice.constratista.datasource.database.AddressEntity
import com.invoice.constratista.datasource.database.DateEntity
import com.invoice.constratista.datasource.database.event.*
import com.invoice.constratista.datasource.database.event.budget.*
import com.invoice.constratista.utils.toDate
import java.util.Date
import com.invoice.constratista.controller.event.request.Date as DateModel
import java.sql.Date as SqlDate

object Mapper {
    fun EventWithBudgets.toEventEntity(): EventEntity {
        val eventEntity = EventEntity(
            event.id,
            event.state,
            event.note,
            event.eventName,
            java.sql.Date(Date().time)
        )
        val budgetEntities = mutableListOf<BudgetEntity>()
        val noteEntities: MutableList<NoteEntity> = mutableListOf()
        val scheduleEntities: MutableList<ScheduleEntity> = mutableListOf()
        val dateEntities: MutableList<DateEntity> = mutableListOf()

        budgets.forEach {
            budgetEntities.add(
                it.toBudgetEntity(
                    eventEntity
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
                ScheduleEntity(
                    it.schedule.id,
                    java.sql.Date(it.schedule.date.toDate()!!.time),
                    it.schedule.state,
                    it.schedule.note,
                    it.schedule.name
                )
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
            dateEntities.add(DateEntity(it.id, it.idReference, java.sql.Date(it.date.toDate()!!.time), it.name))
        }
        eventEntity.budgetEntities.addAll(budgetEntities)
        eventEntity.noteEntities.addAll(noteEntities)
        eventEntity.scheduleEntities.addAll(scheduleEntities)
        eventEntity.dateEntities.addAll(dateEntities)
        return eventEntity
    }

    private fun BudgetWithParts.toBudgetEntity(eventEntity: EventEntity): BudgetEntity {
        val budgetEntity = budget.toBudgetEntity()
        parts.forEach {
            val partEntity = PartEntity(it.part.id, it.part.number, it.part.quantity, it.part.discount)
            partEntity.budget = budgetEntity
            partEntity.reserved = it.reserved.toReservedEntity()
        }
        budgetEntity.event = eventEntity
        return budgetEntity
    }

    private fun Reserved.toReservedEntity(): ReservedEntity {
        val reserved: ReservedEntity
        var date: Date?
        date = if (dateExpiry!!.isNotEmpty()) dateExpiry.toDate()
        else Date()
        if (date == null) date = Date()
        reserved = ReservedEntity(id, SqlDate(date.time))
        return reserved
    }

    private fun Budget.toBudgetEntity() =
        BudgetEntity(id, number, java.sql.Date(date.toDate()!!.time), conditions, status)

    fun List<EventEntity>.toEventWithBudgets(): List<EventWithBudgets?> {
        val events = mutableListOf<EventWithBudgets?>()
        this.forEach {
            events.add(it.toEvent())
        }
        return events
    }

    fun EventEntity.toEvent(): EventWithBudgets? = EventWithBudgets(
        Event(
            this.id,
            this.customerEntity!!.id,
            this.state,
            this.note,
            this.eventName
        ),
        budgets = getBudgets(this.budgetEntities, this.customerEntity!!.id),
        notes = getNotes(this.noteEntities),
        schedules = getSchedules(this.scheduleEntities),
        dates = getDates(this.dateEntities)
    )

    private fun getSchedules(scheduleEntities: MutableList<ScheduleEntity>): List<ScheduleWithAddress> {
        val schedules = mutableListOf<ScheduleWithAddress>()
        scheduleEntities.forEach {
            schedules.add(
                ScheduleWithAddress(
                    schedule = Schedule(
                        it.id,
                        it.event!!.id,
                        it.date.toString(),
                        it.state,
                        it.note,
                        it.address!!.id,
                        it.name
                    ),
                    address = it.address!!
                )
            )
        }
        return schedules
    }

    private fun getDates(dateEntities: MutableList<DateEntity>): List<DateModel> {
        val dates = mutableListOf<DateModel>()
        dateEntities.forEach {
            dates.add(DateModel(it.id, it.eventEntity!!.id, it.date.toString(), it.name))
        }
        return dates
    }

    private fun getNotes(noteEntities: MutableList<NoteEntity>): List<Note> {
        val notes = mutableListOf<Note>()
        noteEntities.forEach {
            notes.add(Note(it.id, it.event!!.id, it.note))
        }
        return notes
    }

    private fun getBudgets(budgetEntities: MutableList<BudgetEntity>, idCustomer: String): List<BudgetWithParts> {
        val list = mutableListOf<BudgetWithParts>()
        budgetEntities.forEach { budget ->
            val partWithReserveds = mutableListOf<PartWithReserved>()
            budget.partEntities.forEach { part ->
                val reserved = part.reserved!!
                partWithReserveds.add(
                    PartWithReserved(
                        Part(part.id, part.number, budget.id, part.quantity, part.discount, part.reserved!!.id),
                        Reserved(
                            reserved.id,
                            reserved.inventory!!.id,
                            part.id,
                            reserved.price!!.id,
                            reserved.dateExpiry.toString()
                        )
                    )
                )
            }
            list.add(
                BudgetWithParts(
                    Budget(
                        budget.id,
                        budget.number,
                        idCustomer,
                        budget.event!!.id,
                        budget.date.toString(),
                        budget.conditions,
                        budget.status
                    ),
                    partWithReserveds
                )
            )
        }
        return list
    }

}

