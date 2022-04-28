package net.voltachat.server.models.token

data class TokenResponse(
    val accessToken: String,
    val expiresIn: Long
)
