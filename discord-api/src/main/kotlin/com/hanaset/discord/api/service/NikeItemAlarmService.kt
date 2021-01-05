package com.hanaset.discord.api.service

import com.hanaset.discord.common.entity.NikeItemEntity
import com.hanaset.discord.common.entity.enums.AlarmStatus
import com.hanaset.discord.common.entity.enums.NikeItemStatus
import com.hanaset.discord.common.repository.DiscordChannelRepository
import com.hanaset.discord.common.repository.NikeItemRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class NikeItemAlarmService(
        private val discordBotService: DiscordBotService,
        private val nikeItemRepository: NikeItemRepository,
        private val discordChannelRepository: DiscordChannelRepository
) {

    fun event10MBeforeAlarm() {

        val discordChannelIds = discordChannelRepository.findAll()

        val before10M = LocalDateTime.now().plusMinutes(10)
        val nikeItems = nikeItemRepository.findByApplyDateBeforeAndStatusAndAlarmStatus(before10M, NikeItemStatus.COMING_SOON, AlarmStatus.BEFORE)

        nikeItems.forEach { it.alarmStatus = AlarmStatus.SEND_START }
        nikeItemRepository.saveAll(nikeItems)

        nikeItems.map { makeMessage(it) }.forEach { message ->
            discordChannelIds.forEach { discordBotService.sendMessage(it.channelId, message) }
        }

        nikeItems.forEach { it.alarmStatus = AlarmStatus.SEND_COMPLETE }
        nikeItemRepository.saveAll(nikeItems)
    }

    fun makeMessage(nikeItemEntity: NikeItemEntity): String {
        return """
            * 상품명 : ${nikeItemEntity.name}
            * 접수시간 : ${nikeItemEntity.applyDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}
            * url : ${nikeItemEntity.url}
        """.trimIndent()
    }
}