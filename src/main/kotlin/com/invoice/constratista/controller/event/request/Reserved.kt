package com.invoice.constratista.controller.event.request

data class Reserved(
    val id: String,
    val idProduct: String,
    val idPart: String,
    val idPrice: String,
    val quantity: String? = "0",
    val dateExpiry: String? = "",
)