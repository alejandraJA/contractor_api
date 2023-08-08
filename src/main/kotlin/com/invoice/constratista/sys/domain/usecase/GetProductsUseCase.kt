package com.invoice.constratista.sys.domain.usecase

import com.invoice.constratista.datasource.database.inventory.ProductEntity
import com.invoice.constratista.datasource.database.inventory.ProductInventoryEntity
import com.invoice.constratista.datasource.repository.sql.ProductInventoryRepository
import com.invoice.constratista.datasource.repository.sql.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class GetProductsUseCase {
    @Autowired
    private lateinit var repository: ProductInventoryRepository
    fun invoke(idProduct: String): ProductInventoryEntity? = repository.findByProductId(idProduct)
}