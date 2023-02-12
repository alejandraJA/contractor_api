package com.invoice.constratista.sys.domain.repository

import com.invoice.constratista.datasource.web.model.DataResponse
import com.invoice.constratista.datasource.web.model.CustomerResponse
import retrofit2.Response

interface FacturapiRepository {
    suspend fun getCustomer(
        token: String
    ): Response<DataResponse<CustomerResponse>>
}