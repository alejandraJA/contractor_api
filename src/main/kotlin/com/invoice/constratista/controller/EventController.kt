package com.invoice.constratista.controller

import com.invoice.constratista.datasource.database.event.EventEntity
import com.invoice.constratista.sys.domain.usecase.EventControllerI
import com.invoice.constratista.utils.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["event"])
class EventController {
    @Autowired
    private lateinit var eventControllerI: EventControllerI

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun save(@RequestBody request: EventEntity): ResponseEntity<Response<EventEntity>> {
        val data = eventControllerI.save(request)
        return ResponseEntity.ok(
            Response(
                status = data != null,
                message = "Event saved",
                data = data
            )
        )
    }

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getEvents(): ResponseEntity<Response<List<EventEntity>>> {
        val data = eventControllerI.get()
        return ResponseEntity.ok(
            Response(
                status = data.isNotEmpty(),
                message = if (data.isEmpty()) "Don´t have Events" else "Successful",
                data = data.ifEmpty { listOf() }
            )
        )
    }
}