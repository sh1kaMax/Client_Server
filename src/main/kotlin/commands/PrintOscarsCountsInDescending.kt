package commands

import requestManager
import utility.CommandResult

class PrintOscarsCountsInDescending: AbsctractCommand("print_field_descending_oscars_count", "вывести значения поля oscarsCount всех элементов в порядке убывания") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.printOscarsCountRequest())
    }
}