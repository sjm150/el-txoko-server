package com.eltxoko.txokoweb.core.loco.dto

import com.eltxoko.txokoweb.core.loco.database.SessionEntity
import java.time.LocalDate

data class SessionFullInfo(
    val openDate: LocalDate,
    val pairLimit: Int,
    val males: List<ParticipantInfo>,
    val females: List<ParticipantInfo>,
) {

    companion object {

        fun of(entity: SessionEntity) = entity.run {
            SessionFullInfo(
                openDate,
                pairLimit,
                maleParticipants.map { ParticipantInfo.of(it) },
                femaleParticipants.map { ParticipantInfo.of(it) },
            )
        }
    }
}
