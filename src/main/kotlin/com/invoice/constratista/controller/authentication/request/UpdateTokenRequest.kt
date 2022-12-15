package com.invoice.constratista.controller.authentication.request

data class UpdateTokenRequest(
    val username: String,
    val token: String,
)