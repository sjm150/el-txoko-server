package com.eltxoko.txokoweb.core.loco.api

import com.eltxoko.txokoweb.common.DateDto
import com.eltxoko.txokoweb.core.loco.dto.CreateSessionRequest
import com.eltxoko.txokoweb.core.loco.dto.GetSessionCsvRequest
import com.eltxoko.txokoweb.core.loco.dto.SessionFullInfo
import com.eltxoko.txokoweb.core.loco.dto.SessionInfo
import com.eltxoko.txokoweb.core.loco.service.SessionService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SessionController(
    private val sessionService: SessionService
) {

    @GetMapping("/api/session/{sessionId}")
    fun getSessionInfoById(
        @PathVariable sessionId: Long,
    ): SessionInfo {
        return sessionService.getSessionInfoById(sessionId)
    }

    @GetMapping("/api/session")
    fun getSessionInfoByDate(
        @Valid @RequestBody dto: DateDto,
    ): SessionInfo {
        return sessionService.getSessionInfoByDate(dto)
    }

    @GetMapping("/api/sessions")
    fun getSessionInfos(
        @RequestParam(required = false, value = "page", defaultValue = "0") page: Int,
        @RequestParam(required = false, value = "size", defaultValue = "10") size: Int,
    ): Page<SessionInfo> {
        return sessionService.getSessionInfos(page, size)
    }

    // TODO: session handling APIs must be OG only

    @PostMapping("/api/admin/session")
    fun createSession(
        @Valid @RequestBody request: CreateSessionRequest,
    ): SessionFullInfo {
        return sessionService.createSession(request)
    }

    @DeleteMapping("/api/admin/session/{sessionId}")
    fun deleteSession(
        @PathVariable sessionId: Long,
    ): SessionFullInfo {
        return sessionService.deleteSession(sessionId)
    }

    @GetMapping("/api/admin/session/{sessionId}")
    fun getSessionFullInfoById(
        @PathVariable sessionId: Long,
    ): SessionFullInfo {
        return sessionService.getSessionFullInfoById(sessionId)
    }

    @GetMapping("/api/admin/session")
    fun getSessionFullInfoByDate(
        @Valid @RequestBody dto: DateDto,
    ): SessionFullInfo {
        return sessionService.getSessionFullInfoByDate(dto)
    }

    @GetMapping("/api/admin/sessions")
    fun getSessionFullInfos(
        @RequestParam(required = false, value = "page", defaultValue = "0") page: Int,
        @RequestParam(required = false, value = "size", defaultValue = "10") size: Int,
    ): Page<SessionFullInfo> {
        return sessionService.getSessionFullInfos(page, size)
    }

    // TODO: 날짜범위 from to 받아서 해당 범위 csv로 주기
    @GetMapping("/api/admin/sessions/csv")
    fun getSessionFullInfoCsv(
        @Valid @RequestBody request: GetSessionCsvRequest,
    ): String {
        return sessionService.getSessionCsv(request)
    }
}
