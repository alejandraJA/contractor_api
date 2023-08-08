package com.invoice.constratista.datasource.database.inventory

data class Availability(
    val idInventory: String,
    val idProduct: String,
    val quantity: Int,
    val reserved: Int,
    val available: Int
)