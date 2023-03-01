package com.eltxoko.txokoweb.core.loco.service

import com.eltxoko.txokoweb.common.DateDto
import com.eltxoko.txokoweb.core.loco.database.SessionEntity
import com.eltxoko.txokoweb.core.loco.database.SessionRepository
import com.eltxoko.txokoweb.core.loco.dto.CreateSessionRequest
import com.eltxoko.txokoweb.core.loco.dto.SessionFullInfo
import com.eltxoko.txokoweb.core.loco.dto.SessionInfo
import com.eltxoko.txokoweb.exception.Exception404
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

interface SessionService {
    fun getSessionInfoById(sessionId: Long): SessionInfo
    fun getSessionInfoByDate(dto: DateDto): SessionInfo
    fun getSessionInfos(page: Int, size: Int): Page<SessionInfo>
    fun createSession(request: CreateSessionRequest): SessionFullInfo
    fun getSessionFullInfoById(sessionId: Long): SessionFullInfo
    fun getSessionFullInfoByDate(dto: DateDto): SessionFullInfo
    fun getSessionFullInfos(page: Int, size: Int): Page<SessionFullInfo>
}

@Service
class SessionServiceImpl(
    private val sessionRepository: SessionRepository,
) : SessionService {

    override fun getSessionInfoById(sessionId: Long): SessionInfo {
        return SessionInfo.of(findSessionEntity(sessionId))
    }

    override fun getSessionInfoByDate(dto: DateDto): SessionInfo {
        return SessionInfo.of(findSessionEntity(dto.date))
    }

    override fun getSessionInfos(page: Int, size: Int): Page<SessionInfo> {
        return sessionRepository.findAllPageable(PageRequest.of(page, size)).map {
            SessionInfo.of(it)
        }
    }

    override fun createSession(request: CreateSessionRequest): SessionFullInfo {
        val sessionEntity = sessionRepository.save(
            SessionEntity(request.openDate, request.pairLimit)
        )

        return SessionFullInfo.of(sessionEntity)
    }

    override fun getSessionFullInfoById(sessionId: Long): SessionFullInfo {
        return SessionFullInfo.of(findSessionEntityWithParticipants(sessionId))
    }

    override fun getSessionFullInfoByDate(dto: DateDto): SessionFullInfo {
        return SessionFullInfo.of(findSessionEntityWithParticipants(dto.date))
    }

    override fun getSessionFullInfos(page: Int, size: Int): Page<SessionFullInfo> {
        return sessionRepository.findAllPageableWithParticipants(PageRequest.of(page, size)).map {
            SessionFullInfo.of(it)
        }
    }

    private fun findSessionEntity(sessionId: Long): SessionEntity {
        return sessionRepository.findByIdOrNull(sessionId)
            ?: throw Exception404("Session not found with id: $sessionId")
    }

    private fun findSessionEntity(date: LocalDate): SessionEntity {
        return sessionRepository.findByOpenDate(date)
            ?: throw Exception404("Session not found in: $date")
    }

    private fun findSessionEntityWithParticipants(sessionId: Long): SessionEntity {
        return sessionRepository.findByIdWithParticipants(sessionId)
            ?: throw Exception404("Session not found with id: $sessionId")
    }

    private fun findSessionEntityWithParticipants(date: LocalDate): SessionEntity {
        return sessionRepository.findByOpenDateWithParticipants(date)
            ?: throw Exception404("Session not found in: $date")
    }
}
