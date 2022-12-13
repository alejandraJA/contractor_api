package com.invoice.constratista.utils

data class Response<T>(
    val status: Boolean,
    val message: String,
    val data: T?,
)
