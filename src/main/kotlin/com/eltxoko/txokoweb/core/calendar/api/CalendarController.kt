package com.eltxoko.txokoweb.core.calendar.api

import com.eltxoko.txokoweb.common.DateDto
import com.eltxoko.txokoweb.core.calendar.dto.CreateScheduleRequest
import com.eltxoko.txokoweb.core.calendar.dto.ScheduleInfo
import com.eltxoko.txokoweb.core.calendar.service.CalendarService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CalendarController(
    private val calendarService: CalendarService,
) {

    @GetMapping("/api/calendar")
    fun getSchedulesInMonth(
        @Valid @RequestBody dto: DateDto,
    ): List<ScheduleInfo> {
        return calendarService.getSchedulesInMonth(dto)
    }

    @PostMapping("/api/calendar/schedule")
    fun createSchedule(
        @Valid @RequestBody request: CreateScheduleRequest,
    ): ScheduleInfo {
        return calendarService.createSchedule(request)
    }
}
