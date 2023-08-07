package com.invoice.constratista.controller

import com.invoice.constratista.datasource.database.inventory.Availability
import com.invoice.constratista.datasource.database.inventory.ProductInventoryEntity
import com.invoice.constratista.datasource.repository.sql.ProductInventoryRepository
import com.invoice.constratista.datasource.repository.sql.ProductRepository
import com.invoice.constratista.datasource.repository.sql.UserRepository
import com.invoice.constratista.utils.Response
import microsoft.sql.DateTimeOffset
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
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
        val date = java.sql.Date(Date().time)
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

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getByIdProduct(@PathVariable id: String): ResponseEntity<Response<ProductInventoryEntity>> {
        val result = inventoryRepository.findByProductId(id)
        return if (result != null)
            ResponseEntity.ok(
                Response(
                    status = true,
                    message = "ok",
                    data = result
                )
            )
        else ResponseEntity.status(400).body(null)
    }

    @RequestMapping(value = ["/available"], method = [RequestMethod.GET])
    @Transactional
    fun getAvailable(): ResponseEntity<Response<List<AvailableInventory>>> {
        val auth = SecurityContextHolder.getContext().authentication
        val user = repository.findUserEntityByUsername(auth.name)!!
        val availableArray = inventoryRepository.getAvailableInventory(user.id)
        if (availableArray.isEmpty()) return ResponseEntity.status(404).build()
        val availableInventory = mutableListOf<AvailableInventory>()
        try {
            availableArray.forEach { resultArray ->
                val id = resultArray[0].toString()
                val modifiedDate = resultArray[1] as Date
                val quantity = (resultArray[2] as Int).toInt()
                val productId = resultArray[3].toString()
                val userId = resultArray[4].toString()

                availableInventory.add(
                    AvailableInventory(
                        id,
                        modifiedDate,
                        quantity,
                        productId,
                        userId
                    )
                )
            }
        } catch (e: Exception) {
            return ResponseEntity.status(400).build()
        }
        return ResponseEntity.ok(
            Response(
                status = true,
                message = "",
                availableInventory
            )
        )
    }

    @RequestMapping(value = ["/{id}/availability"], method = [RequestMethod.GET])
    @Transactional
    fun getAvailability(@PathVariable("id") id: String): ResponseEntity<Response<Availability>> {

        val availabilityList = inventoryRepository.getAvailability(id)
        if (availabilityList.isEmpty()) return ResponseEntity.status(404).build()
        val resultArray: Array<Any> = availabilityList[0]
        val availability: Availability

        try {
            availability = Availability(
                resultArray[0].toString(),
                resultArray[1].toString(),
                (resultArray[2] as Int).toInt(),
                (resultArray[3] as BigDecimal).toInt(),
                (resultArray[4] as BigDecimal).toInt()
            )
        } catch (e: Exception) {
            return ResponseEntity.status(400).build()
        }

        return ResponseEntity.ok(
            Response(
                status = true,
                message = "",
                availability
            )
        )
    }

}

data class AvailableInventory(
    val id: String,
    val modified: Date,
    val quantity: Int,
    val productId: String,
    val userId: String,
)