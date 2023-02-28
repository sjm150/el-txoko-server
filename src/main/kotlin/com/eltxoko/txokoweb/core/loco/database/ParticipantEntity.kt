package com.eltxoko.txokoweb.core.loco.database

import com.eltxoko.txokoweb.common.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "loco_participant")
class ParticipantEntity(
    val email: String,
    val name: String,
    val phoneNumber: String,
    val isMale: Boolean,
    @ManyToOne
    @JoinColumn(name = "session_id")
    val session: SessionEntity,
) : BaseTimeEntity()
