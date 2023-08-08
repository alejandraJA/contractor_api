package com.invoice.constratista.utils

import com.invoice.constratista.controller.event.request.EventWithBudgets

data class Response<T>(
    val status: Boolean,
    val message: String,
    val data: T?,
)
