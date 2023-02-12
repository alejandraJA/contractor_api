package com.invoice.constratista.datasource.web

import com.invoice.constratista.datasource.web.model.DataResponse
import com.invoice.constratista.datasource.web.model.CustomerResponse
import com.invoice.constratista.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface Service {
    @GET("customers")
    suspend fun getCustomer(
        @Header(Constants.AUTHORIZATION) token: String
    ): Response<DataResponse<CustomerResponse>>
}