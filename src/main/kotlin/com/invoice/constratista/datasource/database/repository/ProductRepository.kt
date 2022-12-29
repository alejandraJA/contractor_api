package com.invoice.constratista.datasource.database.repository

import com.invoice.constratista.datasource.database.entity.inventory.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, String>