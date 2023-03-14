package com.eltxoko.txokoweb.core.calendar.database

import com.eltxoko.txokoweb.common.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "schedule")
class ScheduleEntity() : BaseTimeEntity()
