package com.hanaset.discord.api.service.command

import com.hanaset.discord.api.constants.Commands
import com.hanaset.discord.api.service.DiscordDMService
import discord4j.core.event.domain.message.MessageCreateEvent
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class DMDeleteCommand(
        private val discordDMService: DiscordDMService
):Command {

    @PostConstruct
    fun init() {
        val command = "!알람해제"
        Commands.commands[command] = this
    }

    override fun execute(event: MessageCreateEvent) {
        val channel = event.message.channel.block()

        channel?.let { channel ->
            channel.createMessage(getResponse(getWords(event), event)).block()
        }
    }

    override fun getResponse(contents: Map<String, String>?, event: MessageCreateEvent): String {
        return discordDMService.channelDelete(event.message.channelId.asLong(), event.message.author.get().id.asLong())
    }

    override fun getWords(event: MessageCreateEvent): Map<String, String>? {
        return null
    }
}