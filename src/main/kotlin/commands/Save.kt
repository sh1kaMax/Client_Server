package commands

import requestManager
import utility.CommandResult

class Save: AbsctractCommand("save", "сохранить коллекцию в файл") {

    override fun execute(str: String): CommandResult{
        requestManager.saveRequest()
        return CommandResult(true)
    }
}