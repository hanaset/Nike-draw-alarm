package com.hanaset.discord.api.service.command

import com.hanaset.discord.api.constants.Commands
import discord4j.core.event.domain.message.MessageCreateEvent
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class HelpCommand(

): Command {

    @PostConstruct
    fun init() {
        val command = "!명령어"
        Commands.commands[command] = this
    }

    override fun execute(event: MessageCreateEvent) {
        val channel = event.message.channel.block()

        channel?.let { channel ->
            channel.createMessage("안녕하세요. 하나 봇입니다!").block()
        }
    }
}