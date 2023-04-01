package commands

import requestManager
import utility.CommandResult

/**
 * The command updates the element by id
 *
 * @constructor Create empty Update by id
 */
class UpdateById: AbsctractCommand("update id {element}", "обновить значение элемента коллекции, id которого равен заданному"){

    override fun execute(str: String): CommandResult {
        return requestManager.updateByIdRequest(str)
    }
}