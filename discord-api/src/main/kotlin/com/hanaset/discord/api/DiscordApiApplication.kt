package com.hanaset.discord.api

import com.hanaset.discord.common.config.HanaJpaDatabaseConfig
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Import
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*
import javax.annotation.PostConstruct

@EnableScheduling
@SpringBootApplication(scanBasePackages = [
	"com.hanaset.discord.common.*",
	"com.hanaset.discord.api.*"
])

@Import(HanaJpaDatabaseConfig::class)
class DiscordApiApplication(
		private val buildProperties: BuildProperties,
		private val environment: Environment
) : ApplicationListener<ApplicationReadyEvent> {

	private val logger = LoggerFactory.getLogger(DiscordApiApplication::class.java)

	@PostConstruct
	fun init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
	}

	override fun onApplicationEvent(event: ApplicationReadyEvent) {
		logger.info("{} applicationReady, profiles = {}", buildProperties.name, environment.activeProfiles.contentToString())
	}
}

fun main(args: Array<String>) {
	SpringApplicationBuilder(DiscordApiApplication::class.java)
			.listeners(ApplicationPidFileWriter())
			.run(*args)
}