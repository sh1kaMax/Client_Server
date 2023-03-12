package commands

import requestManager
import utility.CommandResult

class UpdateById: AbsctractCommand("update id {element}", "обновить значение элемента коллекции, id которого равен заданному"){

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.updateByIdRequest(str))
    }
}