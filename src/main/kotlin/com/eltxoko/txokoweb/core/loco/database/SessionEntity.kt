package com.eltxoko.txokoweb.core.loco.database

import com.eltxoko.txokoweb.common.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "loco_session")
class SessionEntity(
    val date: LocalDate,
    val limit: Int,
    @OneToMany(mappedBy = "session", cascade = [CascadeType.REMOVE])
    val maleParticipants: List<ParticipantEntity>,
    @OneToMany(mappedBy = "session", cascade = [CascadeType.REMOVE])
    val femaleParticipants: List<ParticipantEntity>,
) : BaseTimeEntity()
