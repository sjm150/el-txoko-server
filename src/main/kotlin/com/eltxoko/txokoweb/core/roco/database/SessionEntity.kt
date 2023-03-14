package com.eltxoko.txokoweb.core.roco.database

import com.eltxoko.txokoweb.common.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "roco_session")
class SessionEntity(
    @field:Column(unique = true)
    val openDate: LocalDate,
    val pairLimit: Int,
    @field:OneToMany(mappedBy = "session", cascade = [CascadeType.REMOVE])
    var maleParticipants: MutableList<ParticipantEntity> = mutableListOf(),
    var maleNumber: Int = 0,
    @field:OneToMany(mappedBy = "session", cascade = [CascadeType.REMOVE])
    var femaleParticipants: MutableList<ParticipantEntity> = mutableListOf(),
    var femaleNumber: Int = 0,
) : BaseTimeEntity()
