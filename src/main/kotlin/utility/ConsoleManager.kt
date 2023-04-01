package utility

import exceptions.IsEmptyException
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * Console
 *
 * @property scan
 * @property commandExecuter
 * @property fabrique
 * @constructor Create Console manager
 */
class ConsoleManager(
    private var scan: Scanner,
    private var commandExecuter: CommandExecuter,
    private var fabrique: Fabrique
) {

    /**
     * Run the program
     *
     */
    fun run() {
        println("Начало работы программы, для справки по командам вызовите help")
        interactiveMode()
    }

    /**
     * run the interactive mode of the program
     *
     */
    private fun interactiveMode() {
        var newInput: Array<String>
        while (true) {
            newInput = (scan.nextLine().trim() + " ").split(Regex(" ")).toTypedArray()
            if ((newInput.size == 3 || newInput.size == 4) && newInput[1][0].toString() == "-") {
                commandExecuter.executeCommand(newInput[0] + " " + newInput[1], newInput[2])
            } else {
                commandExecuter.executeCommand(newInput[0], newInput[1])
            }
        }
    }


    /**
     * run the script mode of the program
     *
     * @param str
     */
    fun scriptMode(str: String) {
        try {
            val file = File(str)
            val scanFile = Scanner(file)
            if (!scanFile.hasNext()) throw IsEmptyException()
            val oldScan = fabrique.getScan()
            fabrique.setScan(scanFile)
            fabrique.setScriptInProgress()
            var newInput: Array<String>

            while (scanFile.hasNextLine()) {
                newInput = (scanFile.nextLine().trim() + " ").split(" ").toTypedArray()
                if ((newInput.size == 3 || newInput.size == 4) && newInput[1][0].toString() == "-") {
                    commandExecuter.executeCommand(newInput[0] + " " + newInput[1], newInput[2])
                } else {
                    commandExecuter.executeCommand(newInput[0], newInput[1])
                }
            }

            fabrique.setScan(oldScan)
            fabrique.setScriptNotInProgress()
        } catch (e: FileNotFoundException) {
            println("error: Файл с таким именем не найден!")
        } catch (e: IsEmptyException) {
            println("error: Файл пустой!")
        }
    }

    /**
     * check the script before run it
     *
     * @param str
     */
    fun checkScript(str: String) {
        try {
            var exitIsThere = false
            var recursionIsThere = false
            var continueDoingScriptCounter = 0

            val file = File(str)
            val scanFile = Scanner(file)
            if (!scanFile.hasNext()) throw IsEmptyException()
            fabrique.setScan(Scanner(System.`in`))
            var newInput: Array<String>

            while (scanFile.hasNextLine() && continueDoingScriptCounter >= 0) {
                newInput = (scanFile.nextLine().trim() + " ").split(" ").toTypedArray()
                if ((newInput.size == 3 || newInput.size == 4) && newInput[1][0].toString() == "-") {
                    if (commandExecuter.checkCommand(newInput[0] + " " + newInput[1])) {
                        if (commandExecuter.checkSymbols(newInput[0] + " " + newInput[1], newInput[2])) {
                            when (newInput[0] + " " + newInput[1]) {
                                "execute_script -delecate" -> {recursionIsThere =  true}
                            }
                        } else continueDoingScriptCounter -= 1
                    } else continueDoingScriptCounter -= 1
                } else {
                    if (commandExecuter.checkCommand(newInput[0])) {
                        if (commandExecuter.checkSymbols(newInput[0], newInput[1])) {
                            when (newInput[0]) {
                                "exit" -> {exitIsThere = true}
                                "execute_script" -> {recursionIsThere = true}
                                "add" -> {continueDoingScriptCounter += 12}
                            }
                        }else continueDoingScriptCounter -= 1
                    } else continueDoingScriptCounter -= 1
                }
            }
            if (exitIsThere) {
                if (!fabrique.askQuestion("В скрипте есть выход из программы, выполнить скрипт?")) continueDoingScriptCounter -= 1
            }

            if (recursionIsThere) {
                if (!fabrique.askQuestion("В скрипте есть рекурсия, выполнить скрипт?")) continueDoingScriptCounter -= 1
            }

            if (continueDoingScriptCounter == 0) {
                scriptMode(str)
            } else println("скрипт отменен")

        } catch (e: FileNotFoundException) {
            println("error: Файл с таким именем не найден!")
        } catch (e: IsEmptyException) {
            println("error: Файл пустой!")
        }
    }
}