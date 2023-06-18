package com.eltxoko.txokoweb.core.calendar.database

import com.eltxoko.txokoweb.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "schedule")
class ScheduleEntity(
    @field:Column(length = 31)
    val name: String,
    val begin: LocalDateTime,
    val end: LocalDateTime,
    val number: Int,
    @field:Column(length = 63)
    val description: String,
    @field:Column(length = 31)
    val inCharge: String,
) : BaseTimeEntity()
