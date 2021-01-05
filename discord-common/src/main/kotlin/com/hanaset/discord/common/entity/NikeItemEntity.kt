package com.hanaset.discord.common.entity

import com.hanaset.discord.common.entity.AbstractBaseAuditEntity
import com.hanaset.discord.common.entity.enums.AlarmStatus
import com.hanaset.discord.common.entity.enums.NikeItemStatus
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "nike_item")
class NikeItemEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = -1,

        val name: String,

        val url: String,

        val image: String,

        @Enumerated(value = EnumType.STRING)
        var status: NikeItemStatus = NikeItemStatus.COMING_SOON,

        @Column(name = "alarm_status")
        @Enumerated(value = EnumType.STRING)
        var alarmStatus: AlarmStatus = AlarmStatus.BEFORE,

        @Column(name = "apply_date")
        val applyDate: LocalDateTime
): AbstractBaseAuditEntity()