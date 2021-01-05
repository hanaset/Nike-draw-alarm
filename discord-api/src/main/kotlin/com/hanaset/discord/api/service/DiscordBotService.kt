package com.hanaset.discord.api.service

import com.hanaset.discord.api.constants.Commands
import com.hanaset.discord.api.exception.DiscordLoginErrorException
import com.hanaset.discord.common.entity.DiscordChannelEntity
import com.hanaset.discord.common.repository.DiscordChannelRepository
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.guild.GuildCreateEvent
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.discordjson.json.MessageCreateRequest
import discord4j.rest.util.MultipartRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class DiscordBotService(
        @Value("\${discord.token}") private val token: String,
        private val discordChannelRepository: DiscordChannelRepository
) {

    private val logger = LoggerFactory.getLogger(DiscordBotService::class.java)
    private val discordClient: GatewayDiscordClient = DiscordClientBuilder.create(token)
            .build()
            .login()
            .onErrorMap { throw DiscordLoginErrorException() }
            .block()!!

    @PostConstruct
    fun init() {
        this.createDispatcher()
    }

    private fun createDispatcher() {

        discordClient.eventDispatcher.on(ReadyEvent::class.java)
                .subscribe { event ->
                    val user = event.self
                    logger.info("Logging in as ${user.username} ${user.discriminator}")
                }

        discordClient.eventDispatcher.on(MessageCreateEvent::class.java)
                .subscribe {
                    logger.info("GuildId: ${it.guildId.get()}, member: ${it.member.get()}, channelId: ${it.message.channelId}, user: ${it.message.author.get()}")

                    val content = it.message.content

                    for(entry in Commands.commands.entries) {
                        if(content.startsWith(entry.key) || content == entry.key) {
                            entry.value.execute(it)
                            break
                        }
                    }

                }

        discordClient.eventDispatcher.on(GuildCreateEvent::class.java)
                .subscribe { event ->
                    val channelId = event.guild.systemChannelId.get().asLong()
                    discordChannelRepository.save(DiscordChannelEntity(channelId = channelId))
                }
    }

    fun sendMessage(channelId: Long, message: String) {
        discordClient.restClient.channelService.createMessage(channelId, MultipartRequest(MessageCreateRequest.builder().content(message).build())).block()
        logger.info("sendMessage :: ChannelId: $channelId, Message: $message")
    }
}