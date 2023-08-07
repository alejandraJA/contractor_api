package com.invoice.constratista.controller.event.request

data class Date(
    val id: Long,
    var idReference: String,
    val date: String,
    val name: String,
)