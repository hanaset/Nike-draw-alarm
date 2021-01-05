package com.hanaset.discord.api.exception

import java.lang.RuntimeException

open class DiscordApiException(
        val code: ErrorCode,
        override val message: String? = null
): RuntimeException(
        message ?: code.message
)

class DiscordLoginErrorException: DiscordApiException(ErrorCode.LOGIN_ERROR)