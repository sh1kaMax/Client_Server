package commands

import requestManager
import utility.CommandResult

/**
 * The command saves the collection on a file
 *
 * @constructor Create Save
 */
class Save: AbsctractCommand("save", "сохранить коллекцию в файл") {

    override fun execute(str: String): CommandResult{
        return CommandResult(true, requestManager.saveRequest(str))
    }
}