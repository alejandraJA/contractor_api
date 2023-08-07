package com.invoice.constratista.controller.event.request

data class EventWithBudgets(
    val event: Event,
    val budgets: List<BudgetWithParts>,
    val notes: List<Note>,
    val schedules: List<ScheduleWithAddress>,
    val dates: List<Date>
)