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
import net.voltachat.server.utils.TokenManager
import routes.profile.simpleAuthRouting


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
        install(ContentNegotiation) {
            gson()
        }

        simpleAuthRouting()
    }.start(wait = true)
}
