package com.eltxoko.txokoweb.core.loco.service

import com.eltxoko.txokoweb.core.loco.database.ParticipantRepository
import org.springframework.stereotype.Service

@Service
interface ParticipantService

class ParticipantServiceImpl(
    private val participantRepository: ParticipantRepository,
) : ParticipantService
