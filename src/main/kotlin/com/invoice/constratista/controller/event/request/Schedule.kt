package com.invoice.constratista.controller.event.request

class Schedule(
    val id: String,
    private val idEvent: String,
    val date: String,
    val state: String,
    val note: String,
    private val idAddress: String,
    val name: String
)