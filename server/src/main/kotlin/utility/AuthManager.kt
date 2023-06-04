package utility

import dataBaseHandler
import java.util.*

class AuthManager {
    private val tokenList = LinkedList<String>()

    fun registerUser(str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        return if (listOfArguments.size == 3) {
            if (dataBaseHandler.registerUserToBD(listOfArguments[0], listOfArguments[1])) {
                CommandResult(StateOfResponse.GOOD, "Пользователь успешно зарегистрирован")
            } else CommandResult(StateOfResponse.ERROR, "error: Пользователь с таким никнеймом уже существует")
        } else CommandResult(StateOfResponse.ERROR, "error: Неправильно введены данные")
    }

    private fun generateToken(username: String, password: String): String {
        val strings = listOf(username, password, dataBaseHandler.getRandomString())
        val token = strings.flatMap { it.toList() + "."}.toList().reduce { pri, vet -> "$pri$vet" }.toString().dropLast(1)
//        val token = "$username.$password." + dataBaseHandler.getRandomString()
        tokenList.add(token)
        return token
    }

    fun loginUser(str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        return if (listOfArguments.size == 3) {
            if (dataBaseHandler.validateUser(listOfArguments[0], listOfArguments[1])) {
                CommandResult(StateOfResponse.GOOD, "Авторизация успешно произошла", generateToken(listOfArguments[0], listOfArguments[1]))
            } else CommandResult(StateOfResponse.ERROR, "error: Пользователя с такими данными не существует")
        } else {
            CommandResult(StateOfResponse.ERROR, "error: Неправильно введены данные")}
    }

    fun tokenExist(token: String?): Boolean {
        return if (token == null) {
            false
        } else tokenList.contains(token)
    }
}