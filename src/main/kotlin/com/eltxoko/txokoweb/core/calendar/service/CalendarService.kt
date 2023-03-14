package com.eltxoko.txokoweb.core.calendar.service

import com.eltxoko.txokoweb.core.calendar.database.ScheduleRepository
import org.springframework.stereotype.Service

interface CalendarService

@Service
class CalendarServiceImpl(
    private val scheduleRepository: ScheduleRepository
) : CalendarService
