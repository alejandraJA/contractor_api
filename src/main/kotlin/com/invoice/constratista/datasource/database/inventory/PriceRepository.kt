package com.invoice.constratista.datasource.database.inventory;

import org.springframework.data.jpa.repository.JpaRepository

interface PriceRepository : JpaRepository<PriceEntity, String> {
}