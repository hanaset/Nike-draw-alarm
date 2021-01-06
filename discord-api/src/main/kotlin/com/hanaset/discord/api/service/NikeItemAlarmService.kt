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

    fun getComingSoonItem(): List<NikeItemEntity> {

        return nikeItemRepository.findByStatus(NikeItemStatus.COMING_SOON)
    }

    fun eventApplyAlarm() {
        val discordChannelIds = discordChannelRepository.findAll()
        val nikeItems = nikeItemRepository.findByApplyDateBeforeAndStatusAndAlarmStatus(LocalDateTime.now(), NikeItemStatus.APPLYING, AlarmStatus.EVENT_10M_BEFORE)
        nikeItems.map { makeMessage(it) }.forEach { message ->
            discordChannelIds.forEach { discordBotService.sendMessage(it.channelId, message) }
        }

        nikeItems.forEach { it.alarmStatus = AlarmStatus.APPLYING }
        nikeItemRepository.saveAll(nikeItems)
    }

    fun event10MBeforeAlarm() {

        val discordChannelIds = discordChannelRepository.findAll()

        val before10M = LocalDateTime.now().plusMinutes(10)
        val nikeItems = nikeItemRepository.findByApplyDateBeforeAndStatusAndAlarmStatus(before10M, NikeItemStatus.COMING_SOON, AlarmStatus.BEFORE)

        nikeItems.map { makeMessage(it) }.forEach { message ->
            discordChannelIds.forEach { discordBotService.sendMessage(it.channelId, message) }
        }

        nikeItems.forEach { it.alarmStatus = AlarmStatus.EVENT_10M_BEFORE }
        nikeItemRepository.saveAll(nikeItems)
    }

    fun makeMessage(nikeItemEntity: NikeItemEntity): String {
        return """
            **나이키 스니커즈 드로우 이벤트가 곧 시작됩니다.**
            
            * 상품명 : ${nikeItemEntity.name}
            * 접수시간 : ${nikeItemEntity.applyDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}
            * url : ${nikeItemEntity.url}
        """.trimIndent()
    }
}