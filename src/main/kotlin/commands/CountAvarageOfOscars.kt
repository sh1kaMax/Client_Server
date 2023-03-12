package commands

import requestManager
import utility.CommandResult

class CountAvarageOfOscars: AbsctractCommand("average_of_oscars_count", "вывести среднее значение поля OscarsCount для всех элементов коллекции") {

    override fun execute(str: String): CommandResult {
        return CommandResult(true, requestManager.getAverageOscarsRequest())
    }
}