package commands

import asker

class ExecuteScript: AbsctractCommand("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме") {

    override fun execute(str: String): Boolean {
        if (!str.isEmpty()) {
            if (!asker.getScriptMode()) println("Используется команда " + getName())
            return true
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }

    fun executeOptional(str1: String, str2: String): Boolean {
        if (str1 == "-delecate" && str2.isNotEmpty()) {
            if (!asker.getScriptMode()) println("Используется команда " + getName() + " с дополнительным параметром")
            return true
        }else {
            println("error: Неправильно введен дополнительный параметр!")
            return false
        }
    }
}