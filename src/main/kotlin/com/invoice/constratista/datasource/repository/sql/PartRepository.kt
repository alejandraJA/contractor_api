package com.invoice.constratista.datasource.repository.sql

import com.invoice.constratista.datasource.database.event.budget.PartEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PartRepository : JpaRepository<PartEntity, String> {
    fun findByBudgetId(budgetId: String): List<PartEntity>

}