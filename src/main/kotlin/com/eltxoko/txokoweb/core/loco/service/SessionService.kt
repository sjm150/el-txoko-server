package com.eltxoko.txokoweb.core.loco.service

import com.eltxoko.txokoweb.common.DateDto
import com.eltxoko.txokoweb.core.loco.database.SessionRepository
import com.eltxoko.txokoweb.core.loco.dto.CreateSessionRequest
import com.eltxoko.txokoweb.core.loco.dto.SessionFullInfo
import com.eltxoko.txokoweb.core.loco.dto.SessionInfo
import org.springframework.stereotype.Service

interface SessionService {
    fun getSessionInfoById(sessionId: Long): SessionInfo
    fun getSessionInfoByDate(dto: DateDto): SessionInfo
    fun getSessionInfos(dto: DateDto): SessionInfo
    fun createSession(request: CreateSessionRequest): SessionFullInfo
    fun getSessionFullInfoById(sessionId: Long): SessionInfo
    fun getSessionFullInfoByDate(dto: DateDto): SessionInfo
    fun getSessionFullInfos(dto: DateDto): SessionInfo
}

@Service
class SessionServiceImpl(
    private val sessionRepository: SessionRepository,
) : SessionService {

    override fun getSessionInfoById(sessionId: Long): SessionInfo {
        TODO("Not yet implemented")
    }

    override fun getSessionInfoByDate(dto: DateDto): SessionInfo {
        TODO("Not yet implemented")
    }

    override fun getSessionInfos(dto: DateDto): SessionInfo {
        TODO("Not yet implemented")
    }

    override fun createSession(request: CreateSessionRequest): SessionFullInfo {
        TODO("Not yet implemented")
    }

    override fun getSessionFullInfoById(sessionId: Long): SessionInfo {
        TODO("Not yet implemented")
    }

    override fun getSessionFullInfoByDate(dto: DateDto): SessionInfo {
        TODO("Not yet implemented")
    }

    override fun getSessionFullInfos(dto: DateDto): SessionInfo {
        TODO("Not yet implemented")
    }
}
