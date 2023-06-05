package com.invoice.constratista.datasource.database.event.budget;

import org.springframework.data.jpa.repository.JpaRepository

interface BudgetEntityRepository: JpaRepository<BudgetEntity, String> {
}