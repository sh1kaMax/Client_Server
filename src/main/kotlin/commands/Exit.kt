package commands

import requestManager
import utility.CommandResult


class Exit: AbsctractCommand("exit", "завершить программу (без сохранения в файл)") {

    override fun execute(str: String): CommandResult {
        requestManager.exitRequest()
        return CommandResult(true)
    }
}