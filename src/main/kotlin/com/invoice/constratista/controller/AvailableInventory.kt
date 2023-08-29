package com.invoice.constratista.controller

import java.util.*

data class AvailableInventory(
    val id: String,
    val modified: Date,
    val quantity: Int,
    val productId: String,
    val userId: String,
)