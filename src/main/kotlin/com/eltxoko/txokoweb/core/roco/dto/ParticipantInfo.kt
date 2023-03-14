package com.eltxoko.txokoweb.core.roco.dto

import com.eltxoko.txokoweb.core.roco.database.ParticipantEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ParticipantInfo(
    val participantId: Long,
    val timestamp: String,
    val isMale: Boolean,
    val age: Int,
    val occupation: String,
    val phoneNumber: String,
    val name: String,
    val recommendation: String,
    val email: String,
) {

    fun toCsvLine(openDate: LocalDate): String {
        return "$participantId,$timestamp,$openDate,${if (isMale) "남성" else "여성"},\"$age, $occupation\",$phoneNumber,$name,$recommendation,,$email\n"
    }

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("YYYY. M. d a h:m:s")

        fun of(entity: ParticipantEntity) = entity.run {
            ParticipantInfo(
                id, formatter.format(createdAt!!), isMale, age, occupation, phoneNumber, name, recommendation, email
            )
        }
    }
}
