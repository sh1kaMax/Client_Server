package commands

import requestManager
import utility.CommandResult

class RemoveLowerCountOfOscars: AbsctractCommand("remove_greater", "удалить из коллекции все элементы, превышающие заданное количество оскаров") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.removeLowerOscarsRequest())
    }
}