package net.voltachat.server.models.token

data class TokenResponse(
    val username: String,
    val accessToken: String,
    val expiresIn: Long
)
