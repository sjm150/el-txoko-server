package com.eltxoko.txokoweb.core.loco.service

import com.eltxoko.txokoweb.core.loco.database.SessionRepository
import org.springframework.stereotype.Service

@Service
interface SessionService

class SessionServiceImpl(
    private val sessionRepository: SessionRepository,
) : SessionService
