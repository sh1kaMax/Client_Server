package utility

import asker
import exceptions.IsEmptyException
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class ConsoleManager(
    private var scan: Scanner,
    private var commandList: CommandExecuter
) {

    fun run() {
        println("Начало работы программы, для справки по командам вызовите help")
        interactiveMode()
    }

    private fun interactiveMode() {
        var newInput: Array<String>
        while (true) {
            newInput = (scan.nextLine().trim() + " ").split(Regex(" ")).toTypedArray()
            if ((newInput.size == 3 || newInput.size == 4) && newInput[1][0].toString() == "-") {
                commandList.executeCommand(newInput[0] + " " + newInput[1], newInput[2])
            } else {
                commandList.executeCommand(newInput[0], newInput[1])
            }
        }
    }


    fun scriptMode(str: String) {
        try {
            val file = File(str)
            val scanFile = Scanner(file)
            if (!scanFile.hasNext()) throw IsEmptyException()
            val oldScan = asker.getScan()
            asker.setScan(scanFile)
            asker.setScriptInProgress()
            var newInput: Array<String>

            while (scanFile.hasNextLine()) {
                newInput = (scanFile.nextLine().trim() + " ").split(" ").toTypedArray()
                if ((newInput.size == 3 || newInput.size == 4) && newInput[1][0].toString() == "-") {
                    commandList.executeCommand(newInput[0] + " " + newInput[1], newInput[2])
                } else {
                    commandList.executeCommand(newInput[0], newInput[1])
                }
            }

            asker.setScan(oldScan)
            asker.setScriptNotInProgress()
        } catch (e: FileNotFoundException) {
            println("error: Файл с таким именем не найден!")
        } catch (e: IsEmptyException) {
            println("error: Файл пустой!")
        }
    }

    fun checkScript(str: String) {
        try {
            var exitIsThere = false
            var recursionIsThere = false
            var continueDoingScript = true

            val file = File(str)
            val scanFile = Scanner(file)
            if (!scanFile.hasNext()) throw IsEmptyException()
            asker.setScan(Scanner(System.`in`))
            var newInput: Array<String>

            while (scanFile.hasNextLine() && continueDoingScript) {
                newInput = (scanFile.nextLine().trim() + " ").split(" ").toTypedArray()
                if ((newInput.size == 3 || newInput.size == 4) && newInput[1][0].toString() == "-") {
                    if (commandList.checkCommand(newInput[0] + " " + newInput[1])) {
                        if (commandList.checkSymbols(newInput[0] + " " + newInput[1], newInput[2])) {
                            when (newInput[0] + " " + newInput[1]) {
                                "execute_script -delecate" -> {recursionIsThere =  true}
                            }
                        } else continueDoingScript = false
                    } else continueDoingScript = false
                } else {
                    if (commandList.checkCommand(newInput[0])) {
                        if (commandList.checkSymbols(newInput[0], newInput[1])) {
                            when (newInput[0]) {
                                "exit" -> {exitIsThere = true}
                                "execute_script" -> {recursionIsThere = true}
                            }
                        }else continueDoingScript = false
                    } else continueDoingScript = false
                }
            }
            if (exitIsThere) {
                if (!asker.askQuestion("В скрипте есть выход из программы, выполнить скрипт?")) continueDoingScript = false
            }

            if (recursionIsThere) {
                if (!asker.askQuestion("В скрипте есть рекурсия, выполнить скрипт?")) continueDoingScript = false
            }

            if (continueDoingScript) {
                scriptMode(str)
            } else println("скрипт отменен")

        } catch (e: FileNotFoundException) {
            println("error: Файл с таким именем не найден!")
        } catch (e: IsEmptyException) {
            println("error: Файл пустой!")
        }
    }
}