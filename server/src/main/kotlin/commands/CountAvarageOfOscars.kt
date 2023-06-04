package commands

import requestManager
import utility.CommandResult

/**
 * The command count and prints the average count of oscars
 *
 * @constructor Create Count avarage of oscars
 */
class CountAvarageOfOscars: AbsctractCommand("average_of_oscars_count", "вывести среднее значение поля OscarsCount для всех элементов коллекции") {

    override fun execute(str: String): CommandResult {
        return requestManager.getAverageOscarsRequest(str)
    }
}