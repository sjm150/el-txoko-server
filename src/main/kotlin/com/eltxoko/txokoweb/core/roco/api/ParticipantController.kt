package com.eltxoko.txokoweb.core.roco.api

import com.eltxoko.txokoweb.core.roco.dto.*
import com.eltxoko.txokoweb.core.roco.service.ParticipantService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ParticipantController(
    private val participantService: ParticipantService,
) {

    @PostMapping("/api/email-verification")
    fun verifyEmail(
        @Valid @RequestBody request: EmailVerificationRequest,
    ): String {
        participantService.startEmailVerification(request)
        return "success"
    }

    @PostMapping("/api/session/{sessionId}/participate")
    fun addParticipant(
        @PathVariable sessionId: Long,
        @Valid @RequestBody request: AddParticipantRequest,
    ): ParticipantInfo {
        return participantService.addParticipant(sessionId, request)
    }

    @GetMapping("/api/session/{sessionId}/participation")
    fun checkParticipation(
        @PathVariable sessionId: Long,
        @Valid @RequestBody request: CheckParticipationRequest,
    ): ParticipantInfo {
        return participantService.checkParticipation(sessionId, request)
    }

    // TODO: OG only
    @DeleteMapping("/api/session/{sessionId}/{participantId}")
    fun deleteParticipant(
        @PathVariable sessionId: Long,
        @PathVariable participantId: Long,
    ): SessionFullInfo {
        return participantService.deleteParticipant(sessionId, participantId)
    }
}
