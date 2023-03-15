package commands

import requestManager
import utility.CommandResult

/**
 * The command removes all the elements with lower oscars than given count
 *
 * @constructor Create Remove lower count of oscars
 */
class RemoveLowerCountOfOscars: AbsctractCommand("remove_lower", "удалить из коллекции все элементы, меньшие заданное количество оскаров") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.removeLowerOscarsRequest(str))
    }
}