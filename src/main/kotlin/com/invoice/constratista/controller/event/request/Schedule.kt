package com.invoice.constratista.controller.event.request

class Schedule(
    val id: String,
    val idEvent: String,
    val date: String,
    val state: String,
    val note: String,
    val idAddress: String,
    val name: String
)