package com.invoice.constratista.controller.event

data class Part(
    var id: String,
    val number: Int,
    var idBudget: String,
    val quantity: Int,
    val discount: Double,
    val reservedId: String
)