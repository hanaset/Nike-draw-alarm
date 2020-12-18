package com.rufree.dobi.api.service

import com.rufree.dobi.common.entity.enums.AlarmStatus
import com.rufree.dobi.common.entity.enums.EventItemStatus
import com.rufree.dobi.common.repository.EventItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NikeEventService(
        private val eventItemRepository: EventItemRepository
) {

    private val logger = LoggerFactory.getLogger(NikeEventService::class.java)

    fun checkingEventItem() {
        val now = LocalDateTime.now()
        val items = eventItemRepository.findByStatus(EventItemStatus.COMING_SOON).filter { it.applyDate.isBefore(now) }
        items.forEach {
            it.status = EventItemStatus.APPLYING
            logger.info("${it.name} 의 상태를 변경합니다.")
        }
        eventItemRepository.saveAll(items)
    }


    fun sendEventItem() {
        val items = eventItemRepository.findByStatusAndAlarmStatus(EventItemStatus.APPLYING, AlarmStatus.BEFORE)
        items.forEach {
            it.alarmStatus = AlarmStatus.SEND_START
            eventItemRepository.save(it)

//            sendMessage(it)

            it.alarmStatus = AlarmStatus.SEND_COMPLETE
            eventItemRepository.save(it)
        }
    }
}