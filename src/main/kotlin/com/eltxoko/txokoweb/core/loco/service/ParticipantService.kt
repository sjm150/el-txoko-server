package com.eltxoko.txokoweb.core.loco.service

import com.eltxoko.txokoweb.core.loco.database.ParticipantEntity
import com.eltxoko.txokoweb.core.loco.database.ParticipantRepository
import com.eltxoko.txokoweb.core.loco.dto.AddParticipantRequest
import com.eltxoko.txokoweb.core.loco.dto.ParticipantInfo
import com.eltxoko.txokoweb.core.loco.dto.SessionFullInfo
import com.eltxoko.txokoweb.exception.Exception400
import com.eltxoko.txokoweb.exception.Exception409
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

interface ParticipantService {
    fun addParticipant(sessionId: Long, request: AddParticipantRequest): ParticipantInfo
    fun deleteParticipant(sessionId: Long, participantId: Long): SessionFullInfo
}

@Service
class ParticipantServiceImpl(
    private val participantRepository: ParticipantRepository,
    private val sessionService: SessionService,
) : ParticipantService {

    @Transactional
    override fun addParticipant(sessionId: Long, request: AddParticipantRequest): ParticipantInfo {
        val session = sessionService.getSessionEntity(sessionId)

        session.run {
            if (request.isMale) maleParticipants else femaleParticipants
                .let {
                    if (it.size >= pairLimit) {
                        throw Exception400("인원 마감되었습니다.")
                    }
                    if (it.find { entity -> entity.email == request.email } != null) {
                        throw Exception409("이미 참여 신청한 이메일입니다.")
                    }
                }
        }

        val participant = request.run {
            participantRepository.save(
                ParticipantEntity(
                    email, name, phoneNumber, isMale, session
                )
            )
        }

        if (request.isMale) {
            session.maleNumber++
        } else {
            session.femaleNumber++
        }

        return ParticipantInfo.of(participant)
    }

    @Transactional
    override fun deleteParticipant(sessionId: Long, participantId: Long): SessionFullInfo {
        val session = sessionService.getSessionEntity(sessionId)

        session.maleParticipants.find { it.id == participantId }
            ?.let {
                participantRepository.delete(it)
                session.maleParticipants.remove(it)
                session.maleNumber--
                return SessionFullInfo.of(session)
            }
        session.femaleParticipants.find { it.id == participantId }
            ?.let {
                participantRepository.delete(it)
                session.femaleParticipants.remove(it)
                session.femaleNumber--
                return SessionFullInfo.of(session)
            }
            ?: throw Exception409("Participant not found in session with id: $participantId")
    }
}
