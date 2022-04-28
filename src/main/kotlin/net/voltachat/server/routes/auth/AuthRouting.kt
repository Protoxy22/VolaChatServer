package net.voltachat.server.routes.auth

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import net.voltachat.server.models.auth.UserCredentials
import net.voltachat.server.models.token.TokenResponse
import net.voltachat.server.models.user.User
import net.voltachat.server.utils.TokenManager

fun Application.authRouting() {

    val tokenManager = TokenManager(HoconApplicationConfig(ConfigFactory.load()))

    routing {
        post("/register") {
            val user = call.receive<UserCredentials>()
            val username = user.username
            val password = user.password
            //val users = Database.usersCollection.find().toList()
            //if (users.find { it.username == username && it.password == password } == null) {
            if(true){ //verif utilisateur
                //Database.usersCollection.insertOne(User(username = username, password = password))
                call.respondText("You have registered successfully", status = HttpStatusCode.Created)
            } else {
                call.respondText("User already exists", status = HttpStatusCode.Conflict)
            }
        }

        post("/login") {
            //val user = call.receive<User>()
            //val users= Database.usersCollection.find().toList()
            //val userInDB = users.find { (it.username == user.username) && (it.password == user.password) } ?: return@post call.respondText(text = "No such user found", status = HttpStatusCode.NotFound)
            //val token = tokenManager.generateToken(user)
            //val response = TokenResponse(accessToken = token, expiresIn = tokenManager.expirationDate)
            call.respond(status = HttpStatusCode.OK, message = "OK")
        }

        get("/users") {
            //call.respond(Database.usersCollection.find().toList())
            call.respond(status = HttpStatusCode.OK, message = "Ok")
        }
    }
}

