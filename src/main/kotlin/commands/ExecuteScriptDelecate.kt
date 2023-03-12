package commands

import commandExecuter
import scan
import utility.CommandResult
import utility.ConsoleManager

class ExecuteScriptDelecate: AbsctractCommand("execute_script -delecate file_name", "считать,проверить и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме") {

    override fun execute(str: String): CommandResult {
        val consoleManager = ConsoleManager(scan, commandExecuter)
        consoleManager.checkScript(str)
        return CommandResult(true)
    }
}