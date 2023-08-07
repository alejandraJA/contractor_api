package com.invoice.constratista.datasource.repository.sql;

import com.invoice.constratista.datasource.database.event.budget.BudgetEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BudgetRepository: JpaRepository<BudgetEntity, String>