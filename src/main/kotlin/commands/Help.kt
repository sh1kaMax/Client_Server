package commands

import asker

class Help: AbsctractCommand("help", "вывести справку по доступным командам") {

    override fun execute(str: String): Boolean {
        if (str.isEmpty()) {
            if (!asker.getScriptMode()) println("Используется команда " + getName())
            return true
        } else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}