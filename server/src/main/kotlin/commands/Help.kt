package commands
import requestManager
import utility.CommandResult

/**
 * The command shows names and explanations od the all commands
 *
 * @constructor Create Help
 */
class Help: AbsctractCommand("help", "вывести справку по доступным командам") {

    override fun execute(str: String): CommandResult{
        return requestManager.helpRequest(str)
    }
}