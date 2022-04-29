package net.voltachat.server

import com.typesafe.config.ConfigFactory
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.websocket.*
import net.voltachat.server.routes.chat.chatRouting
import net.voltachat.server.utils.TokenManager
import routes.profile.simpleAuthRouting
import java.time.Duration


fun main() {
    embeddedServer(Netty, port = 8282) {
        val config = HoconApplicationConfig(ConfigFactory.load())
        val tokenManager = TokenManager(config)
        install(Authentication) {
            jwt {
                verifier(tokenManager.verifyToken())
                realm = config.property("realm").getString()
                validate { jwtCredential ->
                    if (!jwtCredential.payload.getClaim("username").asString().isNullOrEmpty()) {
                        JWTPrincipal(payload = jwtCredential.payload)
                    } else {
                        null
                    }
                }
            }
        }

        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(15)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }

        install(ContentNegotiation) {
            gson()
        }

        simpleAuthRouting()
        chatRouting()
    }.start(wait = true)
}
