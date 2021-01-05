package com.hanaset.discord.common.entity

import javax.persistence.*

@Entity
@Table(name = "discord_channel")
class DiscordChannelEntity(
        @Id
        @Column(name = "channel_id")
        val channelId: Long
): AbstractBaseAuditEntity()