package com.invoice.constratista.controller.auth.response

import java.util.*

data class TokenResponse(
    val token: String,
    val expiration: Date,
)