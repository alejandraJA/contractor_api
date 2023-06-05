package com.invoice.constratista.controller

import com.invoice.constratista.controller.event.EventWithBudgets
import com.invoice.constratista.datasource.mapper.Mapper.toEvent
import com.invoice.constratista.datasource.mapper.Mapper.toEventWithBudgets
import com.invoice.constratista.sys.domain.usecase.EventControllerI
import com.invoice.constratista.utils.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["mobile/event"])
class EventMobileController {

    @Autowired
    private lateinit var eventControllerI: EventControllerI

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun save(@RequestBody request: EventWithBudgets): ResponseEntity<Response<EventWithBudgets>> {
        val data = eventControllerI.save(request).toEvent()
        return ResponseEntity.ok(
            Response(
                status = data != null,
                message = "Event saved",
                data = data
            )
        )
    }

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getEvents(): ResponseEntity<Response<List<EventWithBudgets>>> {
        val data = eventControllerI.get().toEventWithBudgets()
        return ResponseEntity.ok(
            Response(
                status = data.isNotEmpty(),
                message = if (data.isEmpty()) "Don´t have Events" else "Successful",
                data = data.ifEmpty { listOf() }
            )
        )
    }

}