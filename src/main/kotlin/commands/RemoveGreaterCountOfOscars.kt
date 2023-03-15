package commands

import requestManager
import utility.CommandResult

/**
 * The command removes all the elements with greater oscars than given count
 *
 * @constructor Create Remove greater count of oscars
 */
class RemoveGreaterCountOfOscars: AbsctractCommand("remove_greater", "удалить из коллекции все элементы, превышающие заданное количество оскаров") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.removeGreaterOscarsRequest(str))
    }
}