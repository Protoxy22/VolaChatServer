package net.voltachat.server.utils

import net.voltachat.server.models.user.User

class UserManager {

    val userList = mutableListOf<User>()

    init {
        userList.add(User("user1", "pass1"))
        userList.add(User("user2", "pass2"))
        userList.add(User("user3", "pass3"))
    }

    fun attemptLogin(username: String, password: String) : Boolean{
        userList.forEach { it ->
            if(it.username == username){
                if(it.password == password){
                    return true
                }
            }
        }
        return false
    }

}