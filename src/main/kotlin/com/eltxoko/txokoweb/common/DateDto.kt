package com.eltxoko.txokoweb.common

import org.springframework.format.annotation.DateTimeFormat

data class DateDto(
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    val date: String,
)
