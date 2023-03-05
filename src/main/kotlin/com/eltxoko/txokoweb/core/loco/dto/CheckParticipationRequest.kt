package com.eltxoko.txokoweb.core.loco.dto

import jakarta.validation.constraints.Email
import org.hibernate.validator.constraints.Length

data class CheckParticipationRequest(
    @field:Email(message = "올바르지 않은 형식의 이메일입니다.")
    @field:Length(max = 15)
    val email: String,
    @field:Length(min = 4, max = 31)
    val password: String,
)
