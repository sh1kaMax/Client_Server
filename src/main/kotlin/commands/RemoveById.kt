package commands

import requestManager
import utility.CommandResult

class RemoveById: AbsctractCommand("remove_by_id id", "удалить элемент из коллекции по его id") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.removeByIdRequest(str))
    }
}