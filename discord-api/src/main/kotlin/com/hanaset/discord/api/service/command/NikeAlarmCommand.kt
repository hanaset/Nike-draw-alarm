package com.hanaset.discord.api.service.command

import com.hanaset.discord.api.constants.Commands
import com.hanaset.discord.api.service.NikeItemAlarmService
import com.hanaset.discord.common.repository.NikeItemRepository
import discord4j.core.event.domain.message.MessageCreateEvent
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter
import javax.annotation.PostConstruct

@Service
class NikeAlarmCommand(
        private val nikeItemAlarmService: NikeItemAlarmService
): Command {

    @PostConstruct
    fun init() {
        val command = "!이벤트현황"
        Commands.commands[command] = this
    }

    override fun execute(event: MessageCreateEvent) {
        val channel = event.message.channel.block()

        channel?.let { channel ->
            channel.createMessage(getResponse(getWords(event), event)).block()
        }
    }

    override fun getResponse(contents: Map<String, String>?, event: MessageCreateEvent): String {
        val items = nikeItemAlarmService.getComingSoonItem()
        val message = items.mapIndexed { index, nikeItemEntity ->
            "[${nikeItemEntity.applyDate.format(DateTimeFormatter.ofPattern("MM월 dd일 HH:mm"))}]\t **${nikeItemEntity.name}**"
        }.joinToString("\n")

        return "현재 드로우 예정 상품은 ${items.size}개입니다.\n\n${message}"
    }

    override fun getWords(event: MessageCreateEvent): Map<String, String>? {
        return null
    }
}