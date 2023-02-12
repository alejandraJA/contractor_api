package com.invoice.constratista.datasource.repository.sql;

import com.invoice.constratista.datasource.database.FacturapiAccessEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccessRepository : JpaRepository<FacturapiAccessEntity, String> {
    fun findByUserId(userId: Long): FacturapiAccessEntity
}