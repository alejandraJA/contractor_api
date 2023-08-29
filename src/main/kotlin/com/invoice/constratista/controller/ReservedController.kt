package com.invoice.constratista.controller

import com.invoice.constratista.datasource.database.event.budget.ReservedEntity
import com.invoice.constratista.datasource.repository.sql.ProductInventoryRepository
import com.invoice.constratista.datasource.repository.sql.ReservedRepository
import com.invoice.constratista.utils.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
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
    lateinit var inventoryRepository: ProductInventoryRepository

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    fun updateProduct(
        @PathVariable id: String,
        @Query("idProduct") idProduct: String
    ): ResponseEntity<Response<ReservedEntity>> {
        val product = inventoryRepository.findByProductId(idProduct)
        val reserved = repository.findById(id).get()
        if (product == null) return ResponseEntity.status(400).build()
        if (product.product == null) return ResponseEntity.status(400).build()
        reserved.inventory = product
        reserved.price = product.product!!.priceEntity

        repository.save(reserved)
        return ResponseEntity.ok(Response(true, "", reserved))
    }

    @RequestMapping(value = ["/{idReserved}"], method = [RequestMethod.DELETE])
    @Transactional
    fun deletePartByReservedId(@PathVariable(name = "idReserved") idReserved: String): ResponseEntity<Response<Boolean>> {
        repository.deletePart(idReserved)
        val status = repository.findById(idReserved).isEmpty
        return ResponseEntity.ok(
            Response(
                status = status,
                message = if (status) "" else "No " + "Delete",
                data = status
            )
        )
    }
}