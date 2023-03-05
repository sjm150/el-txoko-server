package com.eltxoko.txokoweb.core.loco.dto

import com.eltxoko.txokoweb.core.loco.database.ParticipantEntity

data class ParticipantInfo(
    val participantId: Long,
    val email: String,
    val name: String,
    val phoneNumber: String,
    val age: Int,
    val occupation: String,
    val description: String,
) {

    companion object {
        fun of(entity: ParticipantEntity) = entity.run {
            ParticipantInfo(
                id, email, name, phoneNumber, age, occupation, description
            )
        }
    }
}
