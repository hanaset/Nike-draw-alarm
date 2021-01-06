package com.hanaset.discord.common.entity

import javax.persistence.*

@Entity
@Table(name = "discord_channel")
class DiscordChannelEntity(
        @Id
        @Column(name = "channel_id")
        val channelId: Long,

        @Column(name = "user_id")
        val userId: Long
): AbstractBaseAuditEntity()