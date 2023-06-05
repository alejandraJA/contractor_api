package com.invoice.constratista.controller.event

data class PartWithReserved(
    val part: Part,
    val reserved: List<Reserved>
)