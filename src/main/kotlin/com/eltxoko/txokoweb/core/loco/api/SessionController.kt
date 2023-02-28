package com.eltxoko.txokoweb.core.loco.api

import com.eltxoko.txokoweb.core.loco.service.SessionService
import org.springframework.web.bind.annotation.RestController

@RestController
class SessionController(
    private val sessionService: SessionService
)
