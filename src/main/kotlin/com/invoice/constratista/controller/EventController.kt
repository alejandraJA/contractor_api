package com.invoice.constratista.controller

import com.invoice.constratista.controller.event.EventWithBudgets
import com.invoice.constratista.datasource.database.event.EventEntity
import com.invoice.constratista.datasource.repository.sql.EventEntityRepository
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

@RestController
@RequestMapping(value = ["event"])
class EventController {

    @Autowired
    private lateinit var eventRespository: EventEntityRepository

    @Autowired
    private lateinit var repository: UserRepository

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun save(@RequestBody request: EventWithBudgets): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val date = DateTimeOffset.valueOf(Timestamp(Date().time), 1)
        val event: EventEntity
        return ResponseEntity.ok(
            Response(
                status = true,
                message = "event saved",
                data = eventRespository.save(event)
            )
        )
    }

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getEvents(): ResponseEntity<Response<List<EventEntity>>> {
        val id = repository.findUserEntityByUsername(SecurityContextHolder
            .getContext().authentication.name
        )!!.id
        val data = eventRespository.findAllByUserId(id)
        return ResponseEntity.ok(
            Response(
                status = data.isEmpty(),
                message = "",
                data = data.ifEmpty { listOf() }
            )
        )
    }

}