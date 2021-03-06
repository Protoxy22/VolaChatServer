group = "net.volachat.server"
version = "1.0.0"

plugins {
    application
    kotlin("jvm") version "1.6.21"
}


val ktor_version: String by project
val logback_version: String by project

dependencies {
    //Standard libraries
    implementation(kotlin("stdlib"))

    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-websockets:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
    implementation("commons-codec:commons-codec:1.15")
}

repositories {
    mavenCentral()
    mavenLocal()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "net.voltachat.server.MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

application {
    mainClass.set("net.voltachat.server.MainKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
