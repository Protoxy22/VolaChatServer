package net.voltachat.server.routes.auth

import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.voltachat.server.models.auth.UserCredentials
import net.voltachat.server.models.token.TokenResponse
import net.voltachat.server.models.user.User
import net.voltachat.server.utils.TokenManager
import net.voltachat.server.utils.UserManager

fun Application.authRouting() {

    val tokenManager = TokenManager(HoconApplicationConfig(ConfigFactory.load()))
    val userManager = UserManager()


    routing {

        post("/register") {
            val user = call.receive<UserCredentials>()
            val username = user.username
            val password = user.password

            if(userManager.attemptRegister(username, password)) {
                call.respond(status = HttpStatusCode.OK, message = "Ok")
            } else {
                call.respond(status = HttpStatusCode.Unauthorized, message = "Error registering")
            }
        }

        post("/login") {
            val user = call.receive<User>()
            val username = user.username
            val password = user.password

            if(userManager.attemptLogin(username, password)) {
                val token = tokenManager.generateToken(user)
                val response = TokenResponse(username = username, accessToken = token, expiresIn = tokenManager.expirationDate)
                call.respond(status = HttpStatusCode.OK, message = response)
            } else {
                call.respond(status = HttpStatusCode.Unauthorized, message = "Bad login")
            }
        }

        get("/users") {
            //call.respond(Database.usersCollection.find().toList())
            call.respond(userManager.userList.toList())
        }
    }
}

