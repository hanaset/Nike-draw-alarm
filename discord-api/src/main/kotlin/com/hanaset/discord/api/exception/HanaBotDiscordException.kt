package com.hanaset.discord.api.exception

import java.lang.RuntimeException

open class HanaBotDiscordException(
        val code: ErrorCode,
        override val message: String? = null
): RuntimeException(
        message ?: code.message
)

class DiscordLoginErrorException: HanaBotDiscordException(ErrorCode.LOGIN_ERROR)