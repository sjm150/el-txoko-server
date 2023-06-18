package com.eltxoko.txokoweb.core.calendar.service

import com.eltxoko.txokoweb.common.DateDto
import com.eltxoko.txokoweb.core.calendar.database.ScheduleEntity
import com.eltxoko.txokoweb.core.calendar.database.ScheduleRepository
import com.eltxoko.txokoweb.core.calendar.dto.CreateScheduleRequest
import com.eltxoko.txokoweb.core.calendar.dto.ScheduleInfo
import com.eltxoko.txokoweb.exception.Exception400
import com.eltxoko.txokoweb.exception.Exception409
import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface CalendarService {
    fun getSchedulesInMonth(dto: DateDto): List<ScheduleInfo>
    fun createSchedule(request: CreateScheduleRequest): ScheduleInfo
}

@Service
class CalendarServiceImpl(
    private val scheduleRepository: ScheduleRepository
) : CalendarService {

    override fun getSchedulesInMonth(dto: DateDto): List<ScheduleInfo> {
        val year = dto.date.year
        val month = dto.date.month
        val from = LocalDateTime.of(year, month, 1, 0, 0)
        val to = LocalDateTime.of(year, month + 1, 1, 0, 0).minusDays(1)

        return scheduleRepository.findAllByTimeConflict(from, to).map {
            ScheduleInfo.of(it)
        }
    }

    override fun createSchedule(request: CreateScheduleRequest): ScheduleInfo {
        val schedule = request.run {
            if (!begin.isBefore(end)) throw Exception400("일정 시작 시간은 종료 시간보다 빨라야 합니다")

            if (scheduleRepository.findByTimeConflict(begin, end) == null) {
                scheduleRepository.save(
                    ScheduleEntity(name, begin, end, number, description, inCharge)
                )
            } else {
                throw Exception409("같은 시간대에 예정된 일정이 존재합니다")
            }
        }

        return ScheduleInfo.of(schedule)
    }
}
