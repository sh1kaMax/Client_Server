package commands

import utility.CommandResult

/**
 * Absctract Command of the another commands
 *
 * @property name
 * @property explanationOfCommand
 * @constructor Create Absctract command
 */
abstract class AbsctractCommand(
    private val name: String,
    private val explanationOfCommand: String,
) {


    /**
     * Get name
     *
     * @return name of the command
     */
    fun getName(): String {
        return name
    }

    /**
     * Get explanation of command
     *
     * @return explanation of the command
     */
    fun getExplanationOfCommand(): String {
        return explanationOfCommand
    }

    /**
     * Execute the command
     *
     * @param str
     * @return result of the command
     */
    abstract fun execute(str: String): CommandResult

    override fun toString(): String {
        return "AbsctractCommand(name='$name', explanationOfCommand='$explanationOfCommand')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbsctractCommand) return false

        if (name != other.name) return false
        if (explanationOfCommand != other.explanationOfCommand) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + explanationOfCommand.hashCode()
        return result
    }

}