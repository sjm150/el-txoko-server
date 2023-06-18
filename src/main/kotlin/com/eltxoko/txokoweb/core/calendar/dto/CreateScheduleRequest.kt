package com.eltxoko.txokoweb.core.calendar.dto

import jakarta.persistence.Column
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime

data class CreateScheduleRequest(
    @field:Length(max = 31)
    val name: String,
    @field:Future
    val begin: LocalDateTime,
    @field:Future
    val end: LocalDateTime,
    @field:Positive
    val number: Int,
    @field:Length(max = 63)
    val description: String,
    @field:Column(length = 31)
    val inCharge: String,
)
