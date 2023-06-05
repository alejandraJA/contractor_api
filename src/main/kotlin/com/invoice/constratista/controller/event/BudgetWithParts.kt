package com.invoice.constratista.controller.event

data class BudgetWithParts(
    val budget: Budget,
    val parts: List<PartWithReserved>
)