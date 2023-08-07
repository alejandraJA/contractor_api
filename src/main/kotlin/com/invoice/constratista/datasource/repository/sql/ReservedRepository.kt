package com.invoice.constratista.datasource.repository.sql;

import com.invoice.constratista.datasource.database.event.budget.ReservedEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReservedRepository : JpaRepository<ReservedEntity, String>