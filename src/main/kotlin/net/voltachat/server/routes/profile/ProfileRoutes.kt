package routes.profile

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.voltachat.server.routes.auth.authRouting

fun Application.simpleAuthRouting(){
    routing {
        this@simpleAuthRouting.authRouting()
        getUserProfile()
    }
}
fun Route.getUserProfile() {
    authenticate() {
        get("/profile") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim("username").asString()
            println("This is the username: $username")
            call.respondText(text = "Hey there, $username", status = HttpStatusCode.OK)
        }
    }
}