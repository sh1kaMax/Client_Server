package commands

import authManager
import utility.CommandResult

class Auth: AbsctractCommand("auth", "зарегистрировать пользователя") {

    override fun execute(str: String): CommandResult {
        return authManager.registerUser(str)
    }
}