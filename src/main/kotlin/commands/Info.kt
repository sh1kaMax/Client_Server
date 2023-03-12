package commands

import requestManager
import utility.CommandResult

class Info : AbsctractCommand("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.") {

    override fun execute(str: String): CommandResult {
        requestManager.infoRequest()
        return CommandResult(true)
    }
}