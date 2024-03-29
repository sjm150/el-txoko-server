package com.eltxoko.txokoweb.core.roco.service

import com.eltxoko.txokoweb.core.roco.database.ParticipantEntity
import com.eltxoko.txokoweb.core.roco.database.ParticipantRepository
import com.eltxoko.txokoweb.core.roco.dto.*
import com.eltxoko.txokoweb.exception.Exception400
import com.eltxoko.txokoweb.exception.Exception401
import com.eltxoko.txokoweb.exception.Exception404
import com.eltxoko.txokoweb.exception.Exception409
import jakarta.transaction.Transactional
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.concurrent.TimeUnit

interface ParticipantService {
    fun startEmailVerification(request: EmailVerificationRequest)
    fun addParticipant(sessionId: Long, request: AddParticipantRequest): ParticipantInfo
    fun checkParticipation(sessionId: Long, request: CheckParticipationRequest): ParticipantInfo
    fun deleteParticipant(sessionId: Long, participantId: Long): SessionFullInfo
}

@Service
class ParticipantServiceImpl(
    private val participantRepository: ParticipantRepository,
    private val emailService: EmailService,
    private val sessionService: SessionService,
    private val passwordEncoder: PasswordEncoder,
    private val redisEmailVerificationTemplate: RedisTemplate<String, String>,
) : ParticipantService {

    private val phoneNumberRegex = "010-\\d{4}-\\d{4}".toRegex()
    private val verificationSecond = 200L

    override fun startEmailVerification(request: EmailVerificationRequest) {
        val code = emailService.createVerificationCode()
        redisEmailVerificationTemplate.opsForValue().set(request.email, code, verificationSecond, TimeUnit.SECONDS)
        emailService.sendVerificationCode(request.email, code)
    }

    @Transactional
    override fun addParticipant(sessionId: Long, request: AddParticipantRequest): ParticipantInfo {
        if (!request.phoneNumber.matches(phoneNumberRegex)) throw Exception400("올바르지 않은 형식의 연락처입니다.")

        if (redisEmailVerificationTemplate.opsForValue().get(request.email) == request.code) {
            redisEmailVerificationTemplate.opsForValue().getAndDelete(request.email)
        } else {
            throw Exception400("인증코드가 일치하지 않습니다.")
        }

        val session = sessionService.getSessionEntity(sessionId)
        session.run {
            if (openDate < LocalDate.now()) {
                throw Exception400("참여 기한이 지났습니다.")
            }

            (if (request.isMale) maleParticipants else femaleParticipants).let {
                if (it.size >= pairLimit) {
                    throw Exception400("인원 마감되었습니다.")
                }
            }

            (maleParticipants + femaleParticipants).let {
                if (it.find { entity -> entity.email == request.email } != null) {
                    throw Exception409("이미 참여 신청한 이메일입니다.")
                }
                if (it.find { entity -> entity.phoneNumber == request.phoneNumber } != null) {
                    throw Exception409("이미 참여 신청한 연락처입니다.")
                }
            }
        }

        val participant = request.run {
            participantRepository.save(
                ParticipantEntity(
                    email, name, phoneNumber, isMale, age, occupation, recommendation, passwordEncoder.encode(password), session
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

    override fun checkParticipation(sessionId: Long, request: CheckParticipationRequest): ParticipantInfo {
        val participant = participantRepository.findBySessionIdAndEmail(sessionId, request.email)
            ?: throw Exception404("해당 이메일로 해당 세션에 신청한 참가자가 존재하지 않습니다.")

        if (passwordEncoder.matches(request.password, participant.password)) {
            return ParticipantInfo.of(participant)
        } else {
            throw Exception401("비밀번호가 일치하지 않습니다.")
        }
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
