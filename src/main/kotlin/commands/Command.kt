package commands

import utility.CommandResult

interface Command {
    fun getExplanationOfCommand(): String
    fun getName(): String
    fun execute(str: String): CommandResult
}