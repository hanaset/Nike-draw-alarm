package com.hanaset.discord.api.rest

import com.hanaset.discord.api.rest.support.RestSupport
import com.hanaset.discord.api.service.NikeItemAlarmService
import com.hanaset.discord.api.service.NikeItemParseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/nike")
class NikeRestController(
        private val nikeItemAlarmService: NikeItemAlarmService,
        private val nikeItemParseService: NikeItemParseService
): RestSupport() {

    @GetMapping("/alarm")
    fun nikeAlarm(): ResponseEntity<*> {
        nikeItemAlarmService.event10MBeforeAlarm()
        return response("ok")
    }

    @GetMapping("/parse")
    fun nikeParse(): ResponseEntity<*> {
        nikeItemParseService.itemParse()
        return response("ok")
    }
}