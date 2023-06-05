package com.invoice.constratista.controller.event

data class Budget(
    val id: String,
    val number: Int,
    val idCustomer: String,
    val idEvent: String,
    val date: String,
    val conditions: String,
    val status: String,
)