package commands
import requestManager
import utility.CommandResult

class Help: AbsctractCommand("help", "вывести справку по доступным командам") {

    override fun execute(str: String): CommandResult{
        requestManager.helpRequest()
        return CommandResult(true)
    }
}