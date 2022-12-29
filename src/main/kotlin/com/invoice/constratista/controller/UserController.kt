package com.invoice.constratista.controller

import com.invoice.constratista.datasource.database.repository.UserRepository
import com.invoice.constratista.utils.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["user"])
@RestController
class UserController {
    @Autowired
    private lateinit var repository: UserRepository

    @RequestMapping(value = ["/"], method = [RequestMethod.GET])
    fun getUser(): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val s = repository.findUserEntityByUsername(authentication.name)
        return ResponseEntity.ok(
            Response(
                status = true,
                message = "ok",
                data = s
            )
        )
    }
}