package com.invoice.constratista.sys.domain.usecase

import com.invoice.constratista.datasource.database.inventory.ProductEntity
import com.invoice.constratista.datasource.repository.sql.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class GetProductsUseCase {
    @Autowired
    private lateinit var repository: ProductRepository
    fun invoke(idProduct: String): ProductEntity = repository.findById(idProduct).get()
}