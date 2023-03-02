package com.eltxoko.txokoweb.core.loco.dto

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Positive
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class CreateSessionRequest(
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    @field:FutureOrPresent
    val openDate: LocalDate,
    @field:Positive
    val pairLimit: Int,
)
