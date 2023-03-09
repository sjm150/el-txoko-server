package com.eltxoko.txokoweb.core.loco.dto

import jakarta.validation.constraints.Email
import org.hibernate.validator.constraints.Length

data class EmailVerificationRequest(
    @field:Email(message = "올바르지 않은 형식의 이메일입니다.")
    @field:Length(max = 31)
    val email: String,
)
