package com.hanaset.discord.api.service.command

import com.hanaset.discord.api.constants.Commands
import discord4j.core.event.domain.message.MessageCreateEvent
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.lang.Exception
import javax.annotation.PostConstruct

val helpBasicResponse = """
안녕하세요. Hana Bot 입니다.

현재 Hana Bot에서 제공하고 있는 기능은 다음과 같습니다.
```
1. 나이키 드로우 알람 (10분전, 응모시작)

2. 나이키 드로우 현황 
[사용법] !이벤트현황

3. 나이키 드로우 알람 DM 설정
[사용법] (Hana Bot과 1대1 채팅에서) !알람설정 

4. 나이키 드로우 알람 DM 해제
[사용법] (Hana Bot과 1대1 채팅에서) !알람해제
```
""".trimIndent()

@Service
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
            channel.createMessage(getResponse(getWords(event), event)).block()
        }
    }

    override fun getResponse(contents: Map<String, String>?, event: MessageCreateEvent): String {
        return helpBasicResponse
    }

    override fun getWords(event: MessageCreateEvent): Map<String, String>? {
        return null
    }
}