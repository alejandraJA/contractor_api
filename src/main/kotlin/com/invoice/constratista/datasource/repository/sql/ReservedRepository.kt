package com.invoice.constratista.datasource.repository.sql

import com.invoice.constratista.datasource.database.event.budget.ReservedEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.query.Procedure
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ReservedRepository : JpaRepository<ReservedEntity, String> {
    @Procedure(name = "delete_part", procedureName = "delete_part")
    @Transactional
    fun deletePart(@Param("id_reserved") idReserved: String)
}