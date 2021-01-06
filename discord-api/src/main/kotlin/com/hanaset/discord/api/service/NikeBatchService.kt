package com.hanaset.discord.api.service

import com.hanaset.discord.common.entity.enums.AlarmStatus
import com.hanaset.discord.common.entity.enums.NikeItemStatus
import com.hanaset.discord.common.repository.NikeItemRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NikeBatchService(
        private val nikeItemRepository: NikeItemRepository
) {

    fun comingsoonToApplying() {
        val nikeItems = nikeItemRepository.findByApplyDateBeforeAndStatusAndAlarmStatus(LocalDateTime.now(), NikeItemStatus.COMING_SOON, AlarmStatus.BEFORE)
        nikeItems.forEach { it.status = NikeItemStatus.APPLYING }

        nikeItemRepository.saveAll(nikeItems)
    }
}