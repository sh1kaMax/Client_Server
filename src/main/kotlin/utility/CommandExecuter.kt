package utility

import collection.MovieGenre
import commands.*
import java.io.File
import java.util.*

class CommandExecuter{

    private var commandList: HashMap<String, Command> = HashMap()

    init {
        doMap()
    }

    private fun doMap() {
        commandList["help"] = Help()
        commandList["info"] = Info()
        commandList["show"] = Show()
        commandList["add"] = Add()
        commandList["update"] = UpdateById()
        commandList["remove_by_id"] = RemoveById()
        commandList["clear"] = Clear()
        commandList["save"] = Save()
        commandList["execute_script"] = ExecuteScript()
        commandList["exit"] = Exit()
        commandList["add_if_min"] = AddIfMinOscarsCount()
        commandList["remove_greater"] = RemoveGreaterCountOfOscars()
        commandList["remove_lower"] = RemoveLowerCountOfOscars()
        commandList["average_of_oscars_count"] = CountAvarageOfOscars()
        commandList["count_greater_than_genre"] = CountGenresGreater()
        commandList["print_field_descending_oscars_count"] = PrintOscarsCountsInDescending()
        commandList["execute_script -delecate"] = ExecuteScriptDelecate()
    }

    fun executeCommand(str1: String, str2: String): Boolean? {
        if (checkCommand(str1)) {
            if (checkSymbols(str1, str2)) {
                println("Используется команда $str1")
                commandList[str1]?.execute(str2).let {
                    if (it?.message != null) println(it.message)
                    return it?.commandComplicated
                }
            } else return false
        } else {
            println("error: Неправильно введена команда!")
            return false
        }
    }

    fun checkCommand(str: String): Boolean {
        return commandList.containsKey(str)
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

    fun help() {
        for (command in commandList) {
            println(command.value.getName() + ": " + command.value.getExplanationOfCommand())
        }
    }
}