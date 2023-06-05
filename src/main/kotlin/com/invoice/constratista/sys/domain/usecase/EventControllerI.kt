package com.invoice.constratista.sys.domain.usecase

import com.invoice.constratista.controller.event.EventWithBudgets
import com.invoice.constratista.datasource.database.event.EventEntity
import com.invoice.constratista.datasource.database.inventory.PriceRepository
import com.invoice.constratista.datasource.mapper.Mapper.toEventEntity
import com.invoice.constratista.datasource.repository.sql.CustomerRepository
import com.invoice.constratista.datasource.repository.sql.EventRepository
import com.invoice.constratista.datasource.repository.sql.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller

@Controller
class EventControllerI {

    @Autowired
    private lateinit var eventRespository: EventRepository

    @Autowired
    private lateinit var getProduct: GetProductsUseCase

    @Autowired
    private lateinit var priceRepository: PriceRepository

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var eventRepository: EventRepository

    fun save(request: EventWithBudgets): EventEntity {
        val eventEntity = request.toEventEntity()
        eventEntity.user = repository.findUserEntityByUsername(
            SecurityContextHolder
                .getContext().authentication.name
        )
        eventEntity.customerEntity = customerRepository.findById(request.event.idCustomer).get()
        eventEntity.budgetEntities.forEachIndexed { indexBudget, budget ->
            budget.partEntities.forEachIndexed { indexPart, part ->
                val idProduct = request.budgets[indexBudget].parts[indexPart].reserved.idProduct
                val idPrice = request.budgets[indexBudget].parts[indexPart].reserved.idPrice
                part.reserved!!.product = getProduct.invoke(idProduct)
                part.reserved!!.price = priceRepository.findById(idPrice).get()
            }
        }
        return eventRespository.save(eventEntity)
    }

    fun save(request: EventEntity): EventEntity {
        return eventRepository.save(request)
    }

    fun get(): MutableList<EventEntity> {
        val id = repository.findUserEntityByUsername(
            SecurityContextHolder
                .getContext().authentication.name
        )!!.id
        return eventRepository.findAllByUserId(id)
    }
}