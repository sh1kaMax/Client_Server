package commands

import requestManager
import utility.CommandResult

class Clear: AbsctractCommand("clear", "очистить коллекцию") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.clearRequest())
    }
}