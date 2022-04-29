package net.voltachat.server.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.voltachat.server.models.user.User
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException


class UserManager {

    val userList = mutableListOf<User>()

    init {
        loadUserListFromFile()
    }

    fun attemptLogin(username: String, password: String): Boolean {
        userList.forEach { it ->
            if (it.username == username) {
                if (it.password == password) {
                    println("Success: $username correctly logged in")
                    return true
                }
            }
        }
        println("Error: $username failed to login")
        return false
    }

    fun attemptRegister(username: String, password: String): Boolean {
        if(username == null || password == null || username == "" || password == ""){
            println("Error: Username or password invalid")
            return false
        }
        userList.forEach { it ->
            if (it.username == username) {
                println("Error: Username already exist in the userlist")
                return false
            }
        }

        println("Success: ($username registered with password $password)")
        userList.add(User(username, password))
        refreshUserListFile()
        return true
    }

    private fun loadUserListFromFile() {
        val gson = Gson()
        var reader: FileReader? = null
        try {
            reader = FileReader("userlist.json")
            var data = reader.readText()
            println(data)
            val itemType = object : TypeToken<List<User>>() {}.type
            val users = gson.fromJson<List<User>>(data, itemType)
            userList.addAll(users)
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    fun refreshUserListFile(){
        val gson = GsonBuilder().setPrettyPrinting().create()
        var writer: FileWriter? = null
        try {
            writer = FileWriter("userlist.json")
            var jsonString = gson.toJson(userList)
            writer.write(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (writer != null) {
                try {
                    writer.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

}