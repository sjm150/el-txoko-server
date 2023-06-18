package com.eltxoko.txokoweb.core.calendar.dto

import com.eltxoko.txokoweb.core.calendar.database.ScheduleEntity
import java.time.LocalDateTime

data class ScheduleInfo(
    val id: Long,
    val name: String,
    val begin: LocalDateTime,
    val end: LocalDateTime,
    val number: Int,
    val description: String,
    val inCharge: String
) {

    companion object {

        fun of(entity: ScheduleEntity) = entity.run {
            ScheduleInfo(id, name, begin, end, number, description, inCharge)
        }
    }
}
