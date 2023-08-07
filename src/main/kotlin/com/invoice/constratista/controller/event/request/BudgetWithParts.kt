package com.invoice.constratista.controller.event.request

data class BudgetWithParts(
    val budget: Budget,
    val parts: List<PartWithReserved>
)