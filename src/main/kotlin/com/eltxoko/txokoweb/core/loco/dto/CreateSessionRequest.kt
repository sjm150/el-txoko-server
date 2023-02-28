package com.eltxoko.txokoweb.core.loco.dto

import jakarta.validation.constraints.Positive
import org.springframework.format.annotation.DateTimeFormat

data class CreateSessionRequest(
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    val date: String,
    @field:Positive
    val limit: Int,
)
