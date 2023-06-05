package com.invoice.constratista.controller

import com.invoice.constratista.datasource.database.inventory.ProductInventoryEntity
import com.invoice.constratista.datasource.database.inventory.ProductInventoryRepository
import com.invoice.constratista.datasource.repository.sql.ProductRepository
import com.invoice.constratista.datasource.repository.sql.UserRepository
import com.invoice.constratista.utils.Response
import microsoft.sql.DateTimeOffset
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp
import java.util.*


@RequestMapping(value = ["product"])
@RestController
class ProductController {
    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var inventoryRepository: ProductInventoryRepository

    @Autowired
    private lateinit var productRepository: ProductRepository


    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun save(@RequestBody request: ProductInventoryEntity): ResponseEntity<Response<Any>> {
        // region start settings
        val authentication = SecurityContextHolder.getContext().authentication
        val date = DateTimeOffset.valueOf(Timestamp(Date().time), 1)
        request.modified = date
        request.product!!.modified = date
        request.costEntities.forEach {
            it.date = date
            it.productInventoryEntity = request
        }
        request.product!!.priceEntities.forEach {
            it.date = date
            it.product = request.product
        }
        request.product!!.taxEntities.forEach {
            it.product = request.product
        }
        productRepository.save(request.product!!)
        request.user = repository.findUserEntityByUsername(authentication.name)
        // endregion
        return ResponseEntity.ok(
            Response(
                status = true,
                message = "product saved",
                data = inventoryRepository.save(request)
            )
        )
    }

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAll() = ResponseEntity.ok(
        Response(
            status = true,
            message = "ok",
            data = inventoryRepository.findAllByUserId(
                repository.findUserEntityByUsername(
                    SecurityContextHolder
                        .getContext().authentication.name
                )!!.id
            )
        )
    )

}