package com.invoice.constratista.utils

import microsoft.sql.DateTimeOffset
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun getDateTimeOffet(): DateTimeOffset? = DateTimeOffset.valueOf(Timestamp(Date().time), 1)

fun String.toDate(): Date? = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(this)