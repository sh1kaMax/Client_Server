package commands

import requestManager
import utility.CommandResult

/**
 * The command clears the collection
 *
 * @constructor Create Clear
 */
class Clear: AbsctractCommand("clear", "очистить коллекцию") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.clearRequest(str))
    }
}