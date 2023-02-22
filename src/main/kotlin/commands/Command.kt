package commands

interface Command {
    fun getExplanationOfCommand(): String
    fun getName(): String
    fun execute(str: String): Boolean
}