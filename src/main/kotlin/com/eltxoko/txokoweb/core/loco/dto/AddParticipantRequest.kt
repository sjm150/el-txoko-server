package com.eltxoko.txokoweb.core.loco.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AddParticipantRequest(
    @field:Email(message = "올바르지 않은 형식의 이메일입니다.")
    val email: String,
    @field:NotBlank(message = "올바르지 않은 형식의 이름입니다.")
    val name: String,
    val phoneNumber: String,
    val isMale: Boolean,
)
