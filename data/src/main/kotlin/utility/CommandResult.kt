package utility

import java.io.Serializable


/**
 * The result of the command
 *
 * @property commandComplicated
 * @property message
 * @constructor Create Command result
 */

data class CommandResult(
    val commandComplicated: StateOfResponse,
    val message: String? = null,
    var serverToken: String? = null
) : Serializable