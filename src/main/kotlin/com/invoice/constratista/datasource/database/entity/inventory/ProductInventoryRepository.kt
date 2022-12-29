package com.invoice.constratista.datasource.database.entity.inventory

import org.springframework.data.jpa.repository.JpaRepository

interface ProductInventoryRepository : JpaRepository<ProductInventoryEntity, String>