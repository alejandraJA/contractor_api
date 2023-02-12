package com.invoice.constratista.datasource.repository.sql

import com.invoice.constratista.datasource.database.inventory.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, String>