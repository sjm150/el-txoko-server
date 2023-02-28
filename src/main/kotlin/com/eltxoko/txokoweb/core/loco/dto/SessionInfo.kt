package com.eltxoko.txokoweb.core.loco.dto

import com.eltxoko.txokoweb.core.loco.database.SessionEntity
import java.time.LocalDate

data class SessionInfo(
    val date: LocalDate,
    val limit: Int,
    val males: List<ParticipantInfo>,
    val females: List<ParticipantInfo>,
) {

    companion object {

        fun of(entity: SessionEntity) = entity.run {
            SessionInfo(
                date,
                limit,
                maleParticipants.map { ParticipantInfo.of(it) },
                femaleParticipants.map { ParticipantInfo.of(it) },
            )
        }
    }
}
