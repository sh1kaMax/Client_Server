package commands

import requestManager
import utility.CommandResult

class Add: AbsctractCommand("add {element}", "добавить новый элемент в коллекцию") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.addRequest(null, null, null, null, null, null, null, null))
    }
}