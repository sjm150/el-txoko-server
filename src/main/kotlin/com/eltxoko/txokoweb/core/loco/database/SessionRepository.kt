package com.eltxoko.txokoweb.core.loco.database

import com.eltxoko.txokoweb.core.loco.database.QParticipantEntity.participantEntity
import com.eltxoko.txokoweb.core.loco.database.QSessionEntity.sessionEntity
import com.eltxoko.txokoweb.core.loco.dto.ParticipantInfo
import com.eltxoko.txokoweb.core.loco.dto.SessionFullInfo
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

interface SessionRepository : JpaRepository<SessionEntity, Long>, SessionRepositorySupport {
    fun findByOpenDate(date: LocalDate): SessionEntity?
}

interface SessionRepositorySupport {
    fun findByIdWithParticipants(id: Long): SessionEntity?
    fun findByOpenDateWithParticipants(date: LocalDate): SessionEntity?
    fun findAllPageable(pageable: Pageable): Page<SessionEntity>
    fun findAllFullInfoPageableWithParticipants(pageable: Pageable): Page<SessionFullInfo>
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

    override fun findAllPageable(pageable: Pageable): Page<SessionEntity> {
        val sessionEntities = queryFactory
            .selectFrom(sessionEntity)
            .orderBy(sessionEntity.id.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageImpl(sessionEntities, pageable, sessionEntities.size.toLong())
    }

    override fun findAllFullInfoPageableWithParticipants(pageable: Pageable): Page<SessionFullInfo> {
        val sessionEntities = queryFactory
            .selectFrom(sessionEntity)
            .orderBy(sessionEntity.id.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
            .reversed()

        val sessionIds = sessionEntities.map { it.id }

        val participantEntities = queryFactory
            .selectFrom(participantEntity)
            .where(participantEntity.session.id.`in`(sessionIds))
            .orderBy(
                participantEntity.session.id.desc(),
                participantEntity.isMale.asc(),
                participantEntity.id.desc()
            )
            .fetch()

        val sessionFullInfos = mutableListOf<SessionFullInfo>()
        sessionEntities.forEach {
            sessionFullInfos.add(SessionFullInfo.empty(it))

            sessionFullInfos.last().run {
                for (i in 0 until it.maleNumber) {
                    males.add(ParticipantInfo.of(participantEntities.removeLast()))
                }
                for (i in 0 until it.femaleNumber) {
                    females.add(ParticipantInfo.of(participantEntities.removeLast()))
                }
            }
        }

        return PageImpl(sessionFullInfos, pageable, sessionFullInfos.size.toLong())
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
