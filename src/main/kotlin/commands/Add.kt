package commands

import requestManager
import utility.CommandResult

/**
 * The command adds a new element to the collection
 *
 * @constructor Create Add
 */
class Add: AbsctractCommand("add {element}", "добавить новый элемент в коллекцию") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.addRequest(null, str))
    }
}