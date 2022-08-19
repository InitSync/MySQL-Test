plugins {
	java
}

group = "me.proton.initsync"
version = "1.0"

repositories {
	mavenCentral()
}

dependencies {
	implementation("redis.clients:jedis:3.6.3")
}