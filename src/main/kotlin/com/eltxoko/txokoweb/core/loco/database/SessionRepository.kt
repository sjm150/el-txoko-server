package com.eltxoko.txokoweb.core.loco.database

import org.springframework.data.jpa.repository.JpaRepository

interface SessionRepository : JpaRepository<SessionEntity, Long>
