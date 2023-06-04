package utility

import commands.AbsctractCommand
import commands.ServerExit
import java.util.*
import kotlin.system.exitProcess

class ServerThread(private val scan: Scanner): Thread() {

    private val serverCommands = HashMap<String, AbsctractCommand>()

    init {
        serverCommands["exit"] = ServerExit()
    }

    override fun run() {
        while (true) {
            if (scan.hasNext()) {
                val newInput = scan.nextLine()
                if (checkServerCommand(newInput)) {
                    println("Выключение сервера")
                    exitProcess(0)
                } else println("error: У сервера только одна команда - exit!")
            }
        }
    }

    private fun checkServerCommand(str: String): Boolean {
        return serverCommands.containsKey(str)
    }
}