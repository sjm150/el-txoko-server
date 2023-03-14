package com.eltxoko.txokoweb.core.roco.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class GetSessionCsvRequest(
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    val from: LocalDate = LocalDate.now(),
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    val to: LocalDate?,
)
