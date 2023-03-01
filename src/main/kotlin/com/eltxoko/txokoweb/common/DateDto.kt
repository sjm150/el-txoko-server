package com.eltxoko.txokoweb.common

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class DateDto(
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    val date: LocalDate,
)
