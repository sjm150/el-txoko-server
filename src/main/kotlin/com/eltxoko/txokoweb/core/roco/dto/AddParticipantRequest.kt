package com.eltxoko.txokoweb.core.roco.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Length

data class AddParticipantRequest(
    @field:Email(message = "올바르지 않은 형식의 이메일입니다.")
    @field:Length(max = 31)
    val email: String,
    val code: String,
    @field:NotBlank(message = "이름을 입력해 주세요.")
    @field:Length(max = 31)
    val name: String,
    @field:Length(max = 15)
    val phoneNumber: String,
    val isMale: Boolean,
    @field:Positive
    val age: Int,
    @field:NotBlank(message = "직업을 입력해 주세요.")
    @field:Length(max = 15)
    val occupation: String,
    @field:NotBlank(message = "알게된 경로를 입력해 주세요.")
    @field:Length(max = 63)
    val recommendation: String,
    @field:Length(min = 4, max = 31)
    val password: String,
)
