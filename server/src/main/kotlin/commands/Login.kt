package commands

import authManager
import utility.CommandResult

class Login : AbsctractCommand("login", "войти на свой аккаунт в системе") {

    override fun execute(str: String): CommandResult {
        return authManager.loginUser(str)
    }
}