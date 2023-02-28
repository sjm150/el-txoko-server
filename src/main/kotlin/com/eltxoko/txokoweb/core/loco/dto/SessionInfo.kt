package com.eltxoko.txokoweb.core.loco.dto

import com.eltxoko.txokoweb.core.loco.database.SessionEntity
import java.time.LocalDate

data class SessionInfo(
    val date: LocalDate,
    val limit: Int,
    val maleNumber: Int,
    val femaleNumber: Int,
) {

    companion object {

        fun of(entity: SessionEntity) = entity.run {
            SessionInfo(
                date,
                limit,
                maleParticipants.size,
                femaleParticipants.size,
            )
        }
    }
}
