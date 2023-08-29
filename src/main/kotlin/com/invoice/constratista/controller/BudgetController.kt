package com.invoice.constratista.controller

import com.invoice.constratista.datasource.database.event.budget.BudgetEntity
import com.invoice.constratista.datasource.database.event.budget.PartEntity
import com.invoice.constratista.datasource.repository.sql.BudgetRepository
import com.invoice.constratista.datasource.repository.sql.PartRepository
import com.invoice.constratista.utils.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["budget"])
class BudgetController {
    @Autowired
    private lateinit var repository: BudgetRepository

    @Autowired
    private lateinit var partRepository: PartRepository

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable id: String): ResponseEntity<Response<BudgetEntity>> = ResponseEntity.ok(
        Response(
            status = true,
            message = "ok",
            data = repository.getReferenceById(id)
        )
    )

    @RequestMapping(value = ["/{id}/createPart"], method = [RequestMethod.POST])
    fun createPart(@PathVariable id: String): ResponseEntity<Response<List<PartEntity>>> {
        repository.createPart(id)
        val parts = partRepository.findByBudgetId(id)
        return ResponseEntity.ok(
            Response(
                status = true,
                message = "create",
                parts,
            )
        )
    }


}