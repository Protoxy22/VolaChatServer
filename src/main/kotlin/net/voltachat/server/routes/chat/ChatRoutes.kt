package net.voltachat.server.routes.chat

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import net.voltachat.server.routes.auth.authRouting
import net.voltachat.server.utils.Connection
import routes.profile.getUserProfile
import java.util.*
import kotlin.collections.LinkedHashSet


fun Application.chatRouting(){
    routing {
        chatRouting()
    }
}

fun Route.chatRouting() {
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    webSocket("/chat") {
        val username = call.request.headers["username"];
        val thisConnection = Connection(this)
        connections += thisConnection
        try {
            send("Connected to the chat, ${connections.count()} users online.")
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                val textWithUsername = "[${username}]: $receivedText"
                connections.forEach {
                    it.session.send(textWithUsername)
                }
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        } finally {
            println("Removing $thisConnection!")
            connections -= thisConnection
        }
    }
}
