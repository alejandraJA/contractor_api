package com.invoice.constratista.controller

import com.invoice.constratista.datasource.database.event.budget.PartEntity
import com.invoice.constratista.datasource.repository.sql.PartRepository
import com.invoice.constratista.utils.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import retrofit2.http.Query

@RestController
@RequestMapping(value = ["part"])
class PartController {
    @Autowired
    private lateinit var repository: PartRepository

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    fun updateQuantity(
        @PathVariable id: String,
        @Query("quantity") quantity: Int
    ): ResponseEntity<Response<PartEntity>> {
        val part = repository.findById(id).get()
        part.quantity = quantity
        repository.save(part)
        return ResponseEntity.ok(Response(true, "ok", part))
    }
}

