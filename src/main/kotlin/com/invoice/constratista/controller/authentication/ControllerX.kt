package com.invoice.constratista.controller.authentication

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["hello"])
@RestController
class ControllerX {
    @RequestMapping(value = ["/"], method = [RequestMethod.GET])
    fun hello() = ResponseEntity.ok("hola")
}