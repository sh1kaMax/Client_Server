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
    private var clientManager: ClientManager,
    private var fabrique: Fabrique
) {

    private var clientProcess = StateOfResponse.GOOD
    /**
     * Run the program
     *
     */
    fun run() {
        println("Для работы нужно авторизоваться (auth/login)")
        interactiveMode()
    }

    /**
     * run the interactive mode of the program
     *
     */
    private fun interactiveMode() {
        var newInput: Array<String>
        while (clientProcess != StateOfResponse.CLIENT_EXIT) {
            newInput = (scan.nextLine().trim() + " ").split(Regex(" ")).toTypedArray()
//            clientProcess = if ((newInput.size == 3 || newInput.size == 4) && newInput[1][0].toString() == "-") {
            clientProcess = if (newInput[0] == "execute_script") {
                if (newInput[1] == "-delecate") {
                    checkScript(newInput[2])
                } else scriptMode(newInput[1])
            } else {
                clientManager.sendCommandToServer(newInput[0], newInput[1])
            }
        }
    }


    /**
     * run the script mode of the program
     *
     * @param str
     */
    private fun scriptMode(str: String): StateOfResponse {
        try {
            val file = File(str)
            val scanFile = Scanner(file)
            if (!scanFile.hasNext()) throw IsEmptyException()
            val oldScan = fabrique.getScan()
            fabrique.setScan(scanFile)
            fabrique.setScriptInProgress()
            var newInput: Array<String>

            while (scanFile.hasNextLine() && clientProcess != StateOfResponse.CLIENT_EXIT) {
                newInput = (scanFile.nextLine().trim() + " ").split(" ").toTypedArray()
                clientProcess = if ((newInput.size == 3 || newInput.size == 4) && newInput[1][0].toString() == "-") {
                    clientManager.sendCommandToServer(newInput[0] + " " + newInput[1], newInput[2])
                } else {
                    clientManager.sendCommandToServer(newInput[0], newInput[1])
                }
            }

            fabrique.setScan(oldScan)
            fabrique.setScriptNotInProgress()
            return clientProcess
        } catch (e: FileNotFoundException) {
            println("error: Файл с таким именем не найден!")
            return StateOfResponse.ERROR
        } catch (e: IsEmptyException) {
            println("error: Файл пустой!")
            return StateOfResponse.ERROR
        }
    }

    /**
     * check the script before run it
     *
     * @param str
     */
    private fun checkScript(str: String): StateOfResponse {
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
                    if (clientManager.checkCommand(newInput[0] + " " + newInput[1])) {
                        if (clientManager.checkSymbols(newInput[0] + " " + newInput[1], newInput[2])) {
                            when (newInput[0] + " " + newInput[1]) {
                                "execute_script -delecate" -> {recursionIsThere =  true}
                            }
                        } else continueDoingScriptCounter -= 1
                    } else continueDoingScriptCounter -= 1
                } else {
                    if (clientManager.checkCommand(newInput[0])) {
                        if (clientManager.checkSymbols(newInput[0], newInput[1])) {
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

            return if (continueDoingScriptCounter == 0) {
                scriptMode(str)
            } else {
                println("скрипт отменен")
                StateOfResponse.ERROR
            }


        } catch (e: FileNotFoundException) {
            println("error: Файл с таким именем не найден!")
            return StateOfResponse.ERROR
        } catch (e: IsEmptyException) {
            println("error: Файл пустой!")
            return StateOfResponse.ERROR
        }
    }
}