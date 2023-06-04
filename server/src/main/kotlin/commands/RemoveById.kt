package commands

import requestManager
import utility.CommandResult

/**
 * The command removes the element from the collection by id
 *
 * @constructor Create Remove by id
 */
class RemoveById: AbsctractCommand("remove_by_id id", "удалить элемент из коллекции по его id") {

    override fun execute(str: String): CommandResult {
        return requestManager.removeByIdRequest(str)
    }
}