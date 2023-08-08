package com.invoice.constratista.datasource.repository.sql

import com.invoice.constratista.datasource.database.inventory.PriceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PriceRepository : JpaRepository<PriceEntity, String> {
}