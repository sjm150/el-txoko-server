package com.eltxoko.txokoweb.core.loco.dto

import com.eltxoko.txokoweb.core.loco.database.ParticipantEntity

data class ParticipantInfo(
    val email: String,
    val name: String,
    val phoneNumber: String,
) {

    companion object {
        fun of(entity: ParticipantEntity) = entity.run { ParticipantInfo(email, name, phoneNumber) }
    }
}
