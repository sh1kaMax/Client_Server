package commands

import requestManager
import utility.CommandResult

class CountGenresGreater: AbsctractCommand("count_greater_than_genre genre", "вывести количество элементов, значения поля genre которых больше заданного") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.countGreaterGenreRequest(str))
    }
}