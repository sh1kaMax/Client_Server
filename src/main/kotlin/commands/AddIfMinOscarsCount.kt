package commands

import requestManager
import utility.CommandResult

/**
 * The command adds a new element to the collection if oscars count is min
 *
 * @constructor Create Add if min oscars count
 */
class AddIfMinOscarsCount: AbsctractCommand("add_if_min {element}", "добавить новый элемент в коллекцию, если количество оскаров меньше, чем у элемента с наименьшим количеством оскаров") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.checkIfMinOscarsCountRequest(str))
    }
}