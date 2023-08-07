package com.invoice.constratista.controller.event.request

data class Note(
    val id: String,
    val idEvent: String,
    val note: String,
)