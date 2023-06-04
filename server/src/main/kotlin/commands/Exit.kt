package commands

import requestManager
import utility.CommandResult


/**
 * The command exits from the program
 *
 * @constructor Create Exit
 */
class Exit: AbsctractCommand("exit", "завершить программу (без сохранения в файл)") {

    override fun execute(str: String): CommandResult {
        return requestManager.exitClientRequest(str)
    }
}