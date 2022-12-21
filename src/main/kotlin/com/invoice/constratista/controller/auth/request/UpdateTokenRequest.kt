package com.invoice.constratista.controller.auth.request

data class UpdateTokenRequest(
    val username: String,
    val token: String,
)