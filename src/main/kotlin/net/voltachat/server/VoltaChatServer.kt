package net.voltachat.server

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers

class VoltaChatServer {

    val selectorManager = ActorSelectorManager(Dispatchers.IO)
    val serverSocket = aSocket(selectorManager).tcp().bind("127.0.0.1", 9002)


}


fun main() {
    VoltaChatServer()
}