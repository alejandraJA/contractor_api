package com.invoice.constratista.datasource.repository.sql

import com.invoice.constratista.datasource.database.inventory.ProductInventoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.query.Procedure
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ProductInventoryRepository : JpaRepository<ProductInventoryEntity, String> {
    fun findAllByUserId(userId: Long): MutableList<ProductInventoryEntity>
    fun findByProductId(productId: String): ProductInventoryEntity?

    @Procedure(name = "get_availability", procedureName = "get_availability")
    @Transactional
    fun getAvailability(@Param("id_inventory") idInventory: String): List<Array<Any>>

    @Procedure(name = "get_available_inventory", procedureName = "get_available_inventory")
    @Transactional
    fun getAvailableInventory(@Param("user_id") userId: Long): List<Array<Any>>

}