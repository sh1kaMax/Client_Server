package commands

import commandExecuter
import scan
import utility.CommandResult
import utility.ConsoleManager

class ExecuteScript: AbsctractCommand("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме") {

    override fun execute(str: String): CommandResult {
        val consoleManager = ConsoleManager(scan, commandExecuter)
        consoleManager.scriptMode(str)
        return CommandResult(true)
    }

}