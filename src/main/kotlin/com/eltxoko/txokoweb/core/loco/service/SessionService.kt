package com.eltxoko.txokoweb.core.loco.service

import com.eltxoko.txokoweb.common.DateDto
import com.eltxoko.txokoweb.core.loco.database.SessionEntity
import com.eltxoko.txokoweb.core.loco.database.SessionRepository
import com.eltxoko.txokoweb.core.loco.dto.CreateSessionRequest
import com.eltxoko.txokoweb.core.loco.dto.GetSessionCsvRequest
import com.eltxoko.txokoweb.core.loco.dto.SessionFullInfo
import com.eltxoko.txokoweb.core.loco.dto.SessionInfo
import com.eltxoko.txokoweb.exception.Exception404
import com.eltxoko.txokoweb.exception.Exception409
import org.springframework.dao.DataIntegrityViolationException
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
    fun deleteSession(sessionId: Long): SessionFullInfo
    fun getSessionFullInfoById(sessionId: Long): SessionFullInfo
    fun getSessionFullInfoByDate(dto: DateDto): SessionFullInfo
    fun getSessionFullInfos(page: Int, size: Int): Page<SessionFullInfo>
    fun getSessionCsv(request: GetSessionCsvRequest): String
    fun getSessionEntity(sessionId: Long): SessionEntity
}

@Service
class SessionServiceImpl(
    private val sessionRepository: SessionRepository,
) : SessionService {

    private val csvTemplate = "ID,타임스탬프,참여하고 싶으신 날,성별,\"나이, 하는일\",연락처,성함,알게된 경로,책임자,이메일 주소\n"

    override fun getSessionInfoById(sessionId: Long): SessionInfo {
        return SessionInfo.of(findSessionEntity(sessionId))
    }

    override fun getSessionInfoByDate(dto: DateDto): SessionInfo {
        return SessionInfo.of(findSessionEntity(dto.date))
    }

    override fun getSessionInfos(page: Int, size: Int): Page<SessionInfo> {
        return sessionRepository.findAllAfterNowPageable(LocalDate.now(), PageRequest.of(page, size)).map {
            SessionInfo.of(it)
        }
    }

    override fun createSession(request: CreateSessionRequest): SessionFullInfo {
        val sessionEntity = try {
            sessionRepository.save(
                SessionEntity(request.openDate, request.pairLimit)
            )
        } catch (e: DataIntegrityViolationException) {
            throw Exception409("해당 날짜에 열린 세션이 이미 존재합니다.")
        }

        return SessionFullInfo.of(sessionEntity)
    }

    override fun deleteSession(sessionId: Long): SessionFullInfo {
        val sessionEntity = findSessionEntityWithParticipants(sessionId)
        sessionRepository.delete(sessionEntity)

        return SessionFullInfo.of(sessionEntity)
    }

    override fun getSessionFullInfoById(sessionId: Long): SessionFullInfo {
        return SessionFullInfo.of(findSessionEntityWithParticipants(sessionId))
    }

    override fun getSessionFullInfoByDate(dto: DateDto): SessionFullInfo {
        return SessionFullInfo.of(findSessionEntityWithParticipants(dto.date))
    }

    override fun getSessionFullInfos(page: Int, size: Int): Page<SessionFullInfo> {
        return sessionRepository.findAllFullInfoPageableWithParticipants(PageRequest.of(page, size))
    }

    override fun getSessionCsv(request: GetSessionCsvRequest): String {
        var csv = csvTemplate

        sessionRepository.findAllFullInfoByDateRangeWithParticipants(request.from, request.to).forEach { session ->
            session.females.forEach { csv += it.toCsvLine(session.openDate) }
            session.males.forEach { csv += it.toCsvLine(session.openDate) }
        }

        return csv
    }

    override fun getSessionEntity(sessionId: Long): SessionEntity {
        return findSessionEntityWithParticipants(sessionId)
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
