package com.hanaset.discord.api.service

import com.hanaset.discord.common.entity.DiscordChannelEntity
import com.hanaset.discord.common.repository.DiscordChannelRepository
import org.springframework.stereotype.Service

@Service
class DiscordDMService(
        private val discordChannelRepository: DiscordChannelRepository
) {

    fun channelSave(channelId: Long, userId: Long) {
        discordChannelRepository.save(DiscordChannelEntity(channelId = channelId, userId = userId))
    }

    fun channelDelete(channelId: Long, userId: Long): String {
        val channel = discordChannelRepository.findById(channelId)

        return if(channel.isPresent) {
            discordChannelRepository.delete(channel.get())
            "정상적으로 삭제되었습니다."
        } else {
            "등록하지 않으셨거나 이미 삭제되었습니다."
        }

    }
}