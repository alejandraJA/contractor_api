package com.invoice.constratista.utils

import microsoft.sql.DateTimeOffset
import java.sql.Timestamp
import java.util.*

fun getDateTimeOffet() = DateTimeOffset.valueOf(Timestamp(Date().time), 1)