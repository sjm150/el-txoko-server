package com.eltxoko.txokoweb.core.roco.database

import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantRepository : JpaRepository<ParticipantEntity, Long> {
    fun findBySessionIdAndEmail(sessionId: Long, email: String): ParticipantEntity?
}
