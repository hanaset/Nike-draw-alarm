package com.hanaset.discord.common.repository

import com.hanaset.discord.common.entity.DiscordChannelEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DiscordChannelRepository: JpaRepository<DiscordChannelEntity, Long> {
}