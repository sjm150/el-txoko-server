package com.eltxoko.txokoweb.core.loco.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class AddParticipantRequest(
    @field:Email(message = "올바르지 않은 형식의 이메일입니다.")
    @field:Length(max = 15)
    val email: String,
    @field:NotBlank(message = "올바르지 않은 형식의 이름입니다.")
    @field:Length(max = 31)
    val name: String,
    @field:Length(max = 15)
    val phoneNumber: String,
    val isMale: Boolean,
    // TODO: 나이, 직업(15), 알게된 경로(63) (NotBlank) 추가
    // TODO: 비밀번호 추가(최소길이4자, 최대31)
)
