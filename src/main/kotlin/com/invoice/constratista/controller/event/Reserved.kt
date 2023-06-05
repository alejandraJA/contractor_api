package com.invoice.constratista.controller.event

data class Reserved(
    val id: String,
    val idProduct: String,
    val idPart: String,
    val idReserved: String,
    val quantity: String? = "0",
    val dateExpiry: String? = "",
)