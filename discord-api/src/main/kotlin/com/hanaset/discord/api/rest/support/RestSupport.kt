package com.hanaset.discord.api.rest.support

import com.hanaset.discord.api.rest.dto.response.Response
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.nio.charset.StandardCharsets

val MEDIA_TYPE_APPLICATION_JSON_UTF8 = MediaType("application", "json", StandardCharsets.UTF_8)
const val MEDIA_TYPE_APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8"

abstract class RestSupport {
    protected open fun <T> response(data: T): ResponseEntity<Any> {
        return ResponseEntity
                .ok()
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .body(Response(data = data, message = "ok"))
    }
}