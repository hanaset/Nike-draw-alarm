package com.hanaset.discord.api.scheduler

import com.hanaset.discord.api.service.NikeItemAlarmService
import com.hanaset.discord.api.service.NikeItemParseService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NikeItemScheduler(
        private val nikeItemParseService: NikeItemParseService,
        private val nikeItemAlarmService: NikeItemAlarmService
) {

    // 하루에 한번
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    fun nikeItemParsing() {
        nikeItemParseService.itemParse()
    }

    // 10분에 한번씩
//    @Scheduled(cron = "0 */10 * * * *", zone = "Asia/Seoul")
    @Scheduled(cron = "* * * * * *", zone = "Asia/Seoul")
    fun nike10MBeforeAlarm() {
        nikeItemAlarmService.event10MBeforeAlarm()
    }
}