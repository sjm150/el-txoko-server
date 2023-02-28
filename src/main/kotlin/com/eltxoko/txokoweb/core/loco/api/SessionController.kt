package com.eltxoko.txokoweb.core.loco.api

import com.eltxoko.txokoweb.common.DateDto
import com.eltxoko.txokoweb.core.loco.dto.CreateSessionRequest
import com.eltxoko.txokoweb.core.loco.dto.SessionFullInfo
import com.eltxoko.txokoweb.core.loco.dto.SessionInfo
import com.eltxoko.txokoweb.core.loco.service.SessionService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SessionController(
    private val sessionService: SessionService
) {

    @GetMapping("/api/session/{sessionId}")
    fun getSessionInfoById(
        @PathVariable sessionId: Long,
    ): SessionInfo {
        TODO()
    }

    @GetMapping("/api/session")
    fun getSessionInfoByDate(
        @Valid @RequestBody dto: DateDto,
    ): SessionInfo {
        TODO()
    }

    @GetMapping("/api/sessions")
    fun getSessionInfos(): Page<SessionInfo> {
        TODO()
    }

    // TODO: session handling APIs must be OG only

    @PostMapping("/api/admin/session")
    fun createSession(
        @Valid @RequestBody request: CreateSessionRequest,
    ): SessionFullInfo {
        TODO()
    }

    @GetMapping("/api/admin/session/{sessionId}")
    fun getSessionFullInfoById(
        @PathVariable sessionId: Long,
    ): SessionFullInfo {
        TODO()
    }

    @GetMapping("/api/admin/session")
    fun getSessionFullInfoByDate(
        @Valid @RequestBody dto: DateDto,
    ): SessionFullInfo {
        TODO()
    }

    @GetMapping("/api/admin/sessions")
    fun getSessionFullInfos(): Page<SessionInfo> {
        TODO()
    }

}
