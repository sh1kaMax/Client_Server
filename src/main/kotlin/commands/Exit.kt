package commands

import asker
import kotlin.system.exitProcess

class Exit: AbsctractCommand("exit", "завершить программу (без сохранения в файл)") {

    override fun execute(str: String): Boolean {
        if (str.isEmpty()){
            if (!asker.getScriptMode()) println("Используется команда " + getName())
            exitProcess(0)
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}