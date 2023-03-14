package com.eltxoko.txokoweb.core.calendar.api

import com.eltxoko.txokoweb.core.calendar.service.CalendarService
import org.springframework.web.bind.annotation.RestController

@RestController
class CalendarController(
    private val calendarService: CalendarService,
)
