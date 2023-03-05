package com.eltxoko.txokoweb.core.loco.database

import com.eltxoko.txokoweb.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "loco_participant")
class ParticipantEntity(
    @field:Column(length = 15)
    val email: String,
    @field:Column(length = 31)
    val name: String,
    @field:Column(length = 15)
    val phoneNumber: String,
    val isMale: Boolean,
    val age: Int,
    @field:Column(length = 15)
    val occupation: String,
    @field:Column(length = 63)
    val description: String,
    val password: String,
    @ManyToOne
    @JoinColumn(name = "session_id")
    val session: SessionEntity,
) : BaseTimeEntity()
