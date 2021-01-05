package com.hanaset.discord.api.client

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class NikeClient {

    private val logger = LoggerFactory.getLogger(NikeClient::class.java)

    fun itemParse(): List<Element> {

        val document = Jsoup.connect("https://www.nike.com/kr/launch/?type=upcoming&activeDate=date-filter:AFTER").get()
        val elements = document.select("[class=launch-list-item item-imgwrap  upcomingItem]")

        val eventItem = mutableListOf<Element>()

        for(element in elements) {
            val text = element.select("[class=txt-subject]").text()

            if(text.contains("응모 시작"))
                eventItem.add(element)

        }

        return eventItem
    }
}