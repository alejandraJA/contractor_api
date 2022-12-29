package com.invoice.constratista.datasource.database.repository

import com.invoice.constratista.datasource.database.entity.event.EventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventEntityRepository : JpaRepository<EventEntity, String>