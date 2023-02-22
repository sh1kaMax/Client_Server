package commands

abstract class AbsctractCommand(name: String, explanationOfCommand: String): Command {
    private var name: String
    private var explanationOfCommand: String

    init {
        this.name = name
        this.explanationOfCommand = explanationOfCommand
    }

    override fun getName(): String {
        return name
    }

    override fun getExplanationOfCommand(): String {
        return explanationOfCommand
    }

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