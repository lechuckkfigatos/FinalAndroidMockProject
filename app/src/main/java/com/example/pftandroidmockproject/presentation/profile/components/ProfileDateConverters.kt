package com.example.pftandroidmockproject.presentation.profile.components

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

fun LocalDate.toUtcEpochMillis(): Long {
    return atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()
}

fun Long.toLocalDateUtc(): LocalDate {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
}
