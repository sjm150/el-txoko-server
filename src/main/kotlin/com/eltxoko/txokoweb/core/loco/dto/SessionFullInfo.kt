package com.eltxoko.txokoweb.core.loco.dto

import com.eltxoko.txokoweb.core.loco.database.SessionEntity
import java.time.LocalDate

data class SessionFullInfo(
    val date: LocalDate,
    val limit: Int,
    val males: List<ParticipantInfo>,
    val females: List<ParticipantInfo>,
) {

    companion object {

        fun of(entity: SessionEntity) = entity.run {
            SessionFullInfo(
                date,
                limit,
                maleParticipants.map { ParticipantInfo.of(it) },
                femaleParticipants.map { ParticipantInfo.of(it) },
            )
        }
    }
}
