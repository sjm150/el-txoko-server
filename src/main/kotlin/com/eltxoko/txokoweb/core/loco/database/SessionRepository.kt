package com.eltxoko.txokoweb.core.loco.database

import com.eltxoko.txokoweb.core.loco.database.QParticipantEntity.participantEntity
import com.eltxoko.txokoweb.core.loco.database.QSessionEntity.sessionEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

interface SessionRepository : JpaRepository<SessionEntity, Long>, SessionRepositorySupport {
    fun findByOpenDate(date: LocalDate): SessionEntity?
    fun findAllPageable(pageable: Pageable): Page<SessionEntity>
}

interface SessionRepositorySupport {
    fun findByIdWithParticipants(id: Long): SessionEntity?
    fun findByOpenDateWithParticipants(date: LocalDate): SessionEntity?
    fun findAllPageableWithParticipants(pageable: Pageable): Page<SessionEntity>
}

@Component
class SessionRepositorySupportImpl(
    private val queryFactory: JPAQueryFactory,
) : SessionRepositorySupport {

    override fun findByIdWithParticipants(id: Long): SessionEntity? {
        val sessionEntity = queryFactory
            .selectFrom(sessionEntity)
            .where(sessionEntity.id.eq(id))
            .fetchOne()

        sessionEntity?.let { joinParticipants(it) }

        return sessionEntity
    }

    override fun findByOpenDateWithParticipants(date: LocalDate): SessionEntity? {
        val sessionEntity = queryFactory
            .selectFrom(sessionEntity)
            .where(sessionEntity.openDate.eq(date))
            .fetchOne()

        sessionEntity?.let { joinParticipants(it) }

        return sessionEntity
    }

    override fun findAllPageableWithParticipants(pageable: Pageable): Page<SessionEntity> {
        val sessionEntities = queryFactory
            .selectFrom(sessionEntity)
            .orderBy(sessionEntity.id.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val sessionIds = sessionEntities.map { it.id }

        val participantEntities = queryFactory
            .selectFrom(participantEntity)
            .where(participantEntity.session.id.`in`(sessionIds))
            .orderBy(
                participantEntity.session.id.asc(),
                participantEntity.isMale.asc()
            )
            .fetch()

        sessionEntities.forEach {
            for (i in 0 until it.maleNumber) {
                it.maleParticipants.add(participantEntities.removeLast())
            }
            for (i in 0 until it.femaleNumber) {
                it.femaleParticipants.add(participantEntities.removeLast())
            }
        }

        return PageImpl(sessionEntities, pageable, sessionEntities.size.toLong())
    }

    private fun joinParticipants(sessionEntity: SessionEntity) {
        val participantEntities = queryFactory
            .selectFrom(participantEntity)
            .where(participantEntity.session.id.eq(sessionEntity.id))
            .fetch()

        sessionEntity.maleParticipants = participantEntities.filter { it.isMale }.toMutableList()
        sessionEntity.femaleParticipants = participantEntities.filter { !it.isMale }.toMutableList()
    }
}
