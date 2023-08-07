package com.invoice.constratista.controller

import com.invoice.constratista.datasource.database.event.EventEntity
import com.invoice.constratista.sys.domain.usecase.EventController
import com.invoice.constratista.utils.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["event"])
class EventDesktopController {
    @Autowired
    private lateinit var eventController: EventController

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun save(@RequestBody request: EventEntity): ResponseEntity<Response<EventEntity>> {
        val data = eventController.save(request)
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
        val data = eventController.get()
        return ResponseEntity.ok(
            Response(
                status = data.isNotEmpty(),
                message = if (data.isEmpty()) "Don´t have Events" else "Successful",
                data = data.ifEmpty { listOf() }
            )
        )
    }
}