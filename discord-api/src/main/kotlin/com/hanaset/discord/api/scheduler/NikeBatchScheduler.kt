package com.hanaset.discord.api.scheduler

import com.hanaset.discord.api.service.NikeBatchService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NikeBatchScheduler(
        private val nikeBatchService: NikeBatchService
) {

    @Scheduled(cron = "1 */10 * * * *", zone = "Asia/Seoul")
    fun comingsoonToApplying() {
        nikeBatchService.comingsoonToApplying()
    }
}