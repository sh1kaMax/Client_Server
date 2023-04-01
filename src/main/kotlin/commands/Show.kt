package commands

import requestManager
import utility.CommandResult

/**
 * The command shows the collection
 *
 * @constructor Create Show
 */
class Show: AbsctractCommand("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"){

    override fun execute(str: String): CommandResult {
        return requestManager.showRequest(str)
    }
}