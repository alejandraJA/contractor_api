package com.invoice.constratista.controller.event

import com.invoice.constratista.datasource.database.AddressEntity

data class ScheduleWithAddress(
    val schedule: Schedule,
    val address: AddressEntity
)