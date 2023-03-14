package com.eltxoko.txokoweb.core.calendar.database

import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository : JpaRepository<ScheduleEntity, Long>
