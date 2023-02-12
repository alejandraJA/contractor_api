package com.invoice.constratista.datasource.repository.web

import com.invoice.constratista.datasource.web.Service
import com.invoice.constratista.datasource.web.model.CustomerResponse
import com.invoice.constratista.datasource.web.model.DataResponse
import com.invoice.constratista.sys.domain.repository.FacturapiRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import retrofit2.Response

@Controller
class FacturapiRepositoryImp : FacturapiRepository {
    @Autowired
    private lateinit var service: Service
    override suspend fun getCustomer(token: String): Response<DataResponse<CustomerResponse>> =
        service.getCustomer(token)
}