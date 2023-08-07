package com.invoice.constratista.controller.event.request

data class PartWithReserved(
    val part: Part,
    val reserved: Reserved
)