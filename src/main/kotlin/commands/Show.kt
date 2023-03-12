package commands

import requestManager
import utility.CommandResult

class Show: AbsctractCommand("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"){

    override fun execute(str: String): CommandResult {
        requestManager.showRequest()
        return CommandResult(true)
    }
}