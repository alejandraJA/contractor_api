package com.invoice.constratista.controller

import com.invoice.constratista.datasource.database.event.budget.ReservedEntity
import com.invoice.constratista.datasource.repository.sql.ProductInventoryRepository
import com.invoice.constratista.datasource.repository.sql.ProductRepository
import com.invoice.constratista.datasource.repository.sql.ReservedRepository
import com.invoice.constratista.utils.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import retrofit2.http.Query

@RestController
@RequestMapping(value = ["reserved"])
class ReservedController {
    @Autowired
    private lateinit var repository: ReservedRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired lateinit var inventoryRepository: ProductInventoryRepository

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    fun updateProduct(
        @PathVariable id: String,
        @Query("idProduct") idProduct: String
    ): ResponseEntity<Response<ReservedEntity>> {
        val product = inventoryRepository.findByProductId(idProduct)
        val reserved = repository.findById(id).get()
        reserved.inventory = product
        repository.save(reserved)
        return ResponseEntity.ok(Response(true, "", reserved))
    }
}