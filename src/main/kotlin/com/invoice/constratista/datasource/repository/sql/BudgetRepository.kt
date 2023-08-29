package com.invoice.constratista.datasource.repository.sql

import com.invoice.constratista.datasource.database.event.budget.BudgetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.query.Procedure
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface BudgetRepository: JpaRepository<BudgetEntity, String> {
    @Procedure(name = "create_part", procedureName = "create_part")
    @Transactional
    fun createPart(@Param("id_budget") idBudget: String)
}