group = "net.volachat.server"
version = "1.0.0"

plugins {
    val kotlinVersion = "1.5.31"
    kotlin("jvm") version kotlinVersion
    application
}

val ktorVersion = "1.6.7"

dependencies {
    //Standard libraries
    implementation(kotlin("stdlib"))

    //Ktor Network, for TCP networking server/client
    implementation("io.ktor:ktor-network:2.0.0")
    implementation("io.ktor:ktor-network-tls:2.0.0")

    // Gson
    implementation("io.ktor:ktor-gson:$ktorVersion")

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation( "io.ktor:ktor-server-netty:$ktorVersion")
    implementation ("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")

    implementation("io.ktor:ktor-gson:$ktorVersion")

    //JWT
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

    implementation("io.ktor:ktor-jackson:$ktorVersion")

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
}
