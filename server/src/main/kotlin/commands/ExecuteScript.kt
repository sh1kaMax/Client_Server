package commands

import utility.CommandResult
import utility.StateOfResponse

/**
 * The command executes the script
 *
 * @constructor Create Execute script
 */
class ExecuteScript: AbsctractCommand("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме") {

    override fun execute(str: String): CommandResult {
//        val consoleManager = ConsoleManager(scan, commandExecuter, fabrique)
//        consoleManager.scriptMode(str)
//        return CommandResult(true)
        return CommandResult(StateOfResponse.GOOD, "")
    }

}