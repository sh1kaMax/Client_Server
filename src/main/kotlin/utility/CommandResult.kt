package utility

/**
 * The result of the command
 *
 * @property commandComplicated
 * @property message
 * @constructor Create Command result
 */
data class CommandResult(
    val commandComplicated: Boolean,
    val message: String? = null
)