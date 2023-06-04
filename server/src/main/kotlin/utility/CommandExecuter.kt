package utility

import commands.*

/**
 * Command executer
 *
 * @constructor Create Command executer
 */
class CommandExecuter{

    private val port = 4378

    private var commandList = LinkedHashMap<String, AbsctractCommand>()


    init {
        makeListOfCommands()
    }

    /**
     * make the list of the commands
     *
     */
    private fun makeListOfCommands() {
        commandList["help"] = Help()
        commandList["info"] = Info()
        commandList["show"] = Show()
        commandList["add"] = Add()
        commandList["update"] = UpdateById()
        commandList["remove_by_id"] = RemoveById()
        commandList["clear"] = Clear()
        commandList["execute_script"] = ExecuteScript()
        commandList["execute_script -delecate"] = ExecuteScriptDelecate()
        commandList["exit"] = Exit()
        commandList["add_if_min"] = AddIfMinOscarsCount()
        commandList["remove_greater"] = RemoveGreaterCountOfOscars()
        commandList["remove_lower"] = RemoveLowerCountOfOscars()
        commandList["average_of_oscars_count"] = CountAvarageOfOscars()
        commandList["count_greater_than_genre"] = CountGenresGreater()
        commandList["print_field_descending_oscars_count"] = PrintOscarsCountsInDescending()
        commandList["auth"] = Auth()
        commandList["login"] = Login()
    }

    /**
     * Check and do the executing of the command
     *
     *
     * @return the result of the command
     */
    fun executeCommand(str1: String, str2: String): CommandResult?{
        return if (checkCommand(str1)) {
            commandList[str1]?.execute(str2)
        } else CommandResult(StateOfResponse.ERROR, "error: Команды $str1 не существует")
    }

    private fun checkCommand(str1: String): Boolean {
        return commandList.containsKey(str1)
    }
    /**
     * print names and explanations of all the commands
     *
     */
    fun help(): String {
        var listOfCommands = ""
        for (command in commandList) {
            listOfCommands += command.value.getName() + ": " + command.value.getExplanationOfCommand() + "\n"
        }
        return listOfCommands.substring(0, listOfCommands.length - 2)
    }
}