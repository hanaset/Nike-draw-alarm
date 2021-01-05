package com.hanaset.discord.api.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
        val status: HttpStatus,
        var message: String
) {

    LOGIN_ERROR(HttpStatus.BAD_REQUEST, "Discord Client Login Error")
}