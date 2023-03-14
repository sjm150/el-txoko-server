package com.eltxoko.txokoweb.core.roco.dto

import com.eltxoko.txokoweb.core.roco.database.SessionEntity
import java.time.LocalDate

data class SessionInfo(
    val sessionId: Long,
    val openDate: LocalDate,
    val pairLimit: Int,
    val maleNumber: Int,
    val femaleNumber: Int,
) {

    companion object {

        fun of(entity: SessionEntity) = entity.run {
            SessionInfo(
                id,
                openDate,
                pairLimit,
                maleNumber,
                femaleNumber,
            )
        }
    }
}
