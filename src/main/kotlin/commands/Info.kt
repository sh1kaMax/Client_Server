package commands

import requestManager
import utility.CommandResult

/**
 * The command show the information about the collection
 *
 * @constructor Create Info
 */
class Info : AbsctractCommand("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.infoRequest(str))
    }
}