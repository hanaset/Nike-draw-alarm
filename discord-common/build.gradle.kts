tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	enabled = false
}

tasks.getByName<Jar>("jar") {
	enabled = true
	baseName = "discord-common"
}

dependencies {

	api(kotlin("reflect"))
	api(kotlin("stdlib-jdk8"))
	api("org.springframework.boot:spring-boot-starter")
	api("org.springframework.boot:spring-boot-starter-data-jpa")
	api("org.springframework.boot:spring-boot-starter-web")

	api("com.google.guava:guava:29.0-jre") // google collect
	api("com.google.code.gson:gson:2.8.5")

	compileOnly("org.projectlombok:lombok")
	runtimeOnly("mysql:mysql-connector-java")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}



}