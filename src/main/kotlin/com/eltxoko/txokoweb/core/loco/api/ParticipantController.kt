package com.eltxoko.txokoweb.core.loco.api

import com.eltxoko.txokoweb.core.loco.dto.AddParticipantRequest
import com.eltxoko.txokoweb.core.loco.dto.CheckParticipationRequest
import com.eltxoko.txokoweb.core.loco.dto.ParticipantInfo
import com.eltxoko.txokoweb.core.loco.dto.SessionFullInfo
import com.eltxoko.txokoweb.core.loco.service.EmailService
import com.eltxoko.txokoweb.core.loco.service.ParticipantService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ParticipantController(
    private val emailService: EmailService,
    private val participantService: ParticipantService,
) {

    @PostMapping("/api/session/verify/{email}")
    fun verifyEmail(
        @PathVariable email: String,
    ): String {
        emailService.startEmailVerification(email)
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
