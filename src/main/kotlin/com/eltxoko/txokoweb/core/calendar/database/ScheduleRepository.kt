package com.eltxoko.txokoweb.core.calendar.database

import com.eltxoko.txokoweb.core.calendar.database.QScheduleEntity.scheduleEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

interface ScheduleRepository : JpaRepository<ScheduleEntity, Long>, ScheduleRepositorySupport


interface ScheduleRepositorySupport {
    fun findByTimeConflict(from: LocalDateTime, to: LocalDateTime): ScheduleEntity?
    fun findAllByTimeConflict(from: LocalDateTime, to: LocalDateTime): List<ScheduleEntity>
}

@Component
class ScheduleRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ScheduleRepositorySupport {

    override fun findByTimeConflict(from: LocalDateTime, to: LocalDateTime): ScheduleEntity? {
        return queryFactory
            .selectFrom(scheduleEntity)
            .where(scheduleEntity.begin.between(from, to)
                .or(scheduleEntity.end.between(from, to))
            )
            .fetchOne()
    }

    override fun findAllByTimeConflict(from: LocalDateTime, to: LocalDateTime): List<ScheduleEntity> {
        return queryFactory
            .selectFrom(scheduleEntity)
            .where(scheduleEntity.begin.between(from, to)
                .or(scheduleEntity.end.between(from, to))
            )
            .fetch()
    }
}