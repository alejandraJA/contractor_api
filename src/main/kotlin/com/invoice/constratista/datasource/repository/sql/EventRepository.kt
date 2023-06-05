package com.invoice.constratista.datasource.repository.sql

import com.invoice.constratista.datasource.database.event.EventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<EventEntity, String> {
    fun findAllByUserId(userId: Long): MutableList<EventEntity>
}