package utility

import collection.MovieGenre
import java.io.*
import java.net.Socket
import java.util.*

/**
 * Command executer
 *
 * @constructor Create Command executer
 */
class ClientManager(private var fabrique: Fabrique){

    private var commandList = ArrayList<String>()
    private val port = 5000
    private lateinit var sock: Socket
    private lateinit var outputStream: ObjectOutputStream
    private lateinit var inputStream: ObjectInputStream
    private var serverToken: String? = null


    init {
        makeListOfCommands()
    }

    fun getUsername(): String? {
        return serverToken?.let { Regex("^\\w+").find(it)?.value }
    }
    /**
     * make the list of the commands
     *
     */
    private fun makeListOfCommands() {
        commandList.add("help")
        commandList.add("info")
        commandList.add("show")
        commandList.add("add")
        commandList.add("update")
        commandList.add("remove_by_id")
        commandList.add("clear")
        commandList.add("save")
        commandList.add("execute_script")
        commandList.add("execute_script -delecate")
        commandList.add("exit")
        commandList.add("add_if_min")
        commandList.add("remove_greater")
        commandList.add("remove_lower")
        commandList.add("average_of_oscars_count")
        commandList.add("count_greater_than_genre")
        commandList.add("print_field_descending_oscars_count")
        commandList.add("auth")
        commandList.add("login")
    }

    /**
     * Check and do the executing of the command
     *
     * @param str1
     * @param str2
     * @return the result of the command
     */
    fun sendCommandToServer(str1: String, str2: String): StateOfResponse {
        try {
            connectToServer()
            outputStream.writeObject(Request(str1, (str2 + " " + fabrique.askForCommandArguments(str1)).trim(), serverToken))
            (inputStream.readObject() as CommandResult).let {
                if (it.message != null) println(it.message)
                serverToken = it.serverToken
                return it.commandComplicated
            }
        } catch (e: NotSerializableException) {
            println("error: Произошла ошибка при откправке данных на сервер!")
            return StateOfResponse.CLIENT_EXIT
        } catch (e: IOException) {
            println("error: Соединение с сервером разорвано!")
            return StateOfResponse.CLIENT_EXIT
        }
    }

    fun checkCommand(str: String): Boolean {
        return commandList.contains(str)
    }

    fun checkSymbols(str1: String, str2: String): Boolean {
        if (str1 in listOf("help", "info", "show", "add", "clear", "save", "exit", "add_if_min", "remove_greater", "remove_lower", "average_of_oscars_count", "print_field_descending_oscars_count")) {
            return if (str2.isEmpty()) {
                true
            } else {
                println("error: После команды $str1 ничего не должно быть!")
                false
            }
        }
        if (str1 in listOf("update", "remove_by_id")) {
            return try {
                str2.toInt()
                true
            } catch (e: NumberFormatException) {
                println("error: id это число!")
                false
            }
        }
        if (str1 in listOf("count_greater_than_genre")) {
            return try {
                MovieGenre.valueOf(str2.uppercase(Locale.getDefault()))
                true
            } catch (e: IllegalArgumentException) {
                println("error: Нету такого жанра!")
                false
            }
        }
        if (str1 in listOf("execute_script", "execute_script -delecate")) {
            return if (File(str2).exists()) {
                true
            } else {
                println("error: Файла $str2 не существует!")
                false
            }
        }
        return false
    }

    private fun connectToServer() {
        try {
            sock = Socket("localhost", port)
            outputStream = ObjectOutputStream(sock.getOutputStream())
            inputStream = ObjectInputStream(sock.getInputStream())
        } catch (e: IOException) {
            println("error: Произошла ошибка при подключении к серверу!")
        }
    }
}