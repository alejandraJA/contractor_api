package com.invoice.constratista.controller

import com.invoice.constratista.datasource.repository.sql.AccessRepository
import com.invoice.constratista.datasource.repository.sql.UserRepository
import com.invoice.constratista.datasource.web.model.CustomerResponse
import com.invoice.constratista.datasource.web.model.DataResponse
import com.invoice.constratista.sys.domain.repository.FacturapiRepository
import com.invoice.constratista.utils.Response
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["customer"])
class CustomerController {

    @Qualifier("provideFacturapiRepository")
    @Autowired
    private lateinit var facturapiRepository: FacturapiRepository

    @Autowired
    private lateinit var accessRepository: AccessRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getCustomers(): ResponseEntity<Response<List<CustomerResponse>>> = runBlocking {
        val deferred: Deferred<DataResponse<CustomerResponse>?> = async {
            val authentication = SecurityContextHolder.getContext().authentication
            val user = withContext(Dispatchers.IO) {
                userRepository.findUserEntityByUsername(authentication.name)
            }
            val access = withContext(Dispatchers.IO) {
                accessRepository.findByUserId(user!!.id)
            }
            facturapiRepository.getCustomer("Bearer ${access.secretKey}").body()
        }
        val list = deferred.await()!!
        return@runBlocking ResponseEntity.ok(
            Response(
                status = list.totalResults != 0,
                message = "Success",
                data = list.data
            )
        )
    }
}