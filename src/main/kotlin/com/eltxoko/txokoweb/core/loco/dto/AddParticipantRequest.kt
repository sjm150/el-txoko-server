package com.eltxoko.txokoweb.core.loco.dto

data class AddParticipantRequest(
    val email: String,
    val name: String,
    val phoneNumber: String,
    val isMale: Boolean,
)
