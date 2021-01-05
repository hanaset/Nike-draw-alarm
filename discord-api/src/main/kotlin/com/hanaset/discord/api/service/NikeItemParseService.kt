package com.hanaset.discord.api.service

import com.hanaset.discord.api.client.NikeClient
import com.hanaset.discord.common.entity.NikeItemEntity
import com.hanaset.discord.common.entity.enums.NikeItemStatus
import com.hanaset.discord.common.repository.NikeItemRepository
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class NikeItemParseService(
        private val nikeClient: NikeClient,
        private val nikeItemRepository: NikeItemRepository
) {

    private val logger = LoggerFactory.getLogger(NikeItemParseService::class.java)

    fun itemParse() {

        val elements = nikeClient.itemParse()
        val nikeItems = elements.mapNotNull { makeEventItem(it) }.filter { validItem(it) }

        logger.info("NikeItemParseService::itemParse ==> ${nikeItems.size}")

        nikeItemRepository.saveAll(nikeItems)
    }

    private fun validItem(nikeItemEntity: NikeItemEntity): Boolean {
        return !nikeItemRepository.existsByUrlAndStatus(nikeItemEntity.url, NikeItemStatus.COMING_SOON)
    }

    private fun makeEventItem(element: Element): NikeItemEntity? {

        val date = LocalDate.parse(element.attr("data-active-date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0"))
        val subElement = element.selectFirst("[class=comingsoon]")
        val name = subElement.attr("title")
        val url = "https://www.nike.com${subElement.attr("href")}"
        val image = element.selectFirst("img").attr("data-src")
        val txtSubject = element.selectFirst("[class=txt-subject]").text()

        val time = when{
            txtSubject.contains("오전") -> {
                LocalTime.parse(txtSubject.substring(3,8))
            }
            txtSubject.contains("오후") -> {
                LocalTime.parse(txtSubject.substring(3,8)).plusHours(12)
            }
            else -> {
                logger.warn("NikeClient::makeEventItem ==> txtSubject Warning $txtSubject")
                return null
            }
        }

        val applyDate = LocalDateTime.of(date, time)

        return NikeItemEntity(name = name, url = url, image = image, applyDate = applyDate)
    }
}