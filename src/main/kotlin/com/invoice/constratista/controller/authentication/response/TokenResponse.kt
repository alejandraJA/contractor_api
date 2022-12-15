package com.invoice.constratista.controller.authentication.response

import java.util.*

data class TokenResponse(
    val token: String,
    val expiration: Date,
)