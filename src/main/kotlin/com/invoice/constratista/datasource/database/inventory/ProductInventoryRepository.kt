package com.invoice.constratista.datasource.database.inventory

import org.springframework.data.jpa.repository.JpaRepository

interface ProductInventoryRepository : JpaRepository<ProductInventoryEntity, String> {
    fun findAllByUserId(userId: Long): MutableList<ProductInventoryEntity>

}