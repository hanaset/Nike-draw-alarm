package com.hanaset.discord.common.repository

import com.hanaset.discord.common.entity.NikeItemEntity
import com.hanaset.discord.common.entity.enums.AlarmStatus
import com.hanaset.discord.common.entity.enums.NikeItemStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface NikeItemRepository: JpaRepository<NikeItemEntity, Long> {

    fun existsByUrlAndStatus(url: String, status: NikeItemStatus): Boolean
    fun findByApplyDateBeforeAndStatusAndAlarmStatus(applyDate: LocalDateTime, status: NikeItemStatus, alarmStatus: AlarmStatus): List<NikeItemEntity>
}