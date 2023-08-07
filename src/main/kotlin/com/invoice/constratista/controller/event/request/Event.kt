package com.invoice.constratista.controller.event.request

data class Event(
    val id: String,
    val idCustomer: String,
    val state: String,
    val note: String,
    val eventName: String,
)