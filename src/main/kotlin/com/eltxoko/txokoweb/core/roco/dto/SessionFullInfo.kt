package com.eltxoko.txokoweb.core.roco.dto

import com.eltxoko.txokoweb.core.roco.database.SessionEntity
import java.time.LocalDate

data class SessionFullInfo(
    val sessionId: Long,
    val openDate: LocalDate,
    val pairLimit: Int,
    val males: MutableList<ParticipantInfo> = mutableListOf(),
    val females: MutableList<ParticipantInfo> = mutableListOf(),
) {

    companion object {

        fun of(entity: SessionEntity) = entity.run {
            SessionFullInfo(
                id,
                openDate,
                pairLimit,
                maleParticipants.map { ParticipantInfo.of(it) }.toMutableList(),
                femaleParticipants.map { ParticipantInfo.of(it) }.toMutableList(),
            )
        }

        fun empty(entity: SessionEntity) = entity.run {
            SessionFullInfo(
                id, openDate, pairLimit
            )
        }
    }
}
