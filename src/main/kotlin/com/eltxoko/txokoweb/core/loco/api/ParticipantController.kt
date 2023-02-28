package com.eltxoko.txokoweb.core.loco.api

import com.eltxoko.txokoweb.core.loco.service.ParticipantService
import org.springframework.web.bind.annotation.RestController

@RestController
class ParticipantController(
    private val participantService: ParticipantService,
)
