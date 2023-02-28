package utility

import asker
import exceptions.IsEmptyException
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class ConsoleManager(commandManager: CommandManager,
                     scan: Scanner,
) {
    private var commandManager: CommandManager
    private var scan: Scanner
    private var checker: Checker = Checker(scan)

    init {
        this.commandManager = commandManager
        this.scan = scan
    }

    fun run() {
        println("Начало работы программы, для справки по командам вызовите help")
        interactiveMode()
    }

    private fun interactiveMode() {
        var newInput: Array<String>
        var commandChecker: Boolean
        do {
            newInput = (scan.nextLine().trim() + " ").split(" ").toTypedArray()
            commandChecker = false

            if(newInput[0] == "help") {
                commandChecker = true
                commandManager.help(newInput[1])
            }

            if(newInput[0] == "exit") {
                commandChecker = true
                commandManager.exit(newInput[1])
            }

            if(newInput[0] == "info") {
                commandChecker = true
                commandManager.info(newInput[1])
            }

            if (newInput[0] == "add") {
                commandChecker = true
                commandManager.add(newInput[1])
            }

            if (newInput[0] == "show") {
                commandChecker = true
                commandManager.show(newInput[1])
            }

            if (newInput[0] == "update") {
                commandChecker = true
                commandManager.update(newInput[1])
            }

            if (newInput[0] == "remove_by_id") {
                commandChecker = true
                commandManager.removeById(newInput[1])
            }

            if (newInput[0] == "clear") {
                commandChecker = true
                commandManager.clear(newInput[1])
            }

            if (newInput[0] == "remove_greater") {
                commandChecker = true
                commandManager.removeGreater(newInput[1])
            }

            if (newInput[0] == "remove_lower") {
                commandChecker = true
                commandManager.removeLower(newInput[1])
            }

            if (newInput[0] == "add_if_min") {
                commandChecker = true
                commandManager.addIfMinOscars(newInput[1])
            }

            if (newInput[0] == "average_of_oscars_count") {
                commandChecker = true
                commandManager.getAverageOfOscars(newInput[1])
            }

            if (newInput[0] == "count_greater_than_genre") {
                commandChecker = true
                commandManager.countGenresGreater(newInput[1])
            }

            if (newInput[0] == "print_field_descending_oscars_count") {
                commandChecker = true
                commandManager.printOscarsCount(newInput[1])
            }

            if (newInput[0] == "save") {
                commandChecker = true
                commandManager.save(newInput[1])
            }

            if (newInput[0] == "execute_script") {
                commandChecker = true
                if (newInput[2].isEmpty()) {
                    if (commandManager.executeScript(newInput[1])) {
                        scriptMode(newInput[1])
                    }
                }else {
                    if (commandManager.executeScriptOptional(newInput[1], newInput[2])) {
                        if (checkScript(newInput[2])) {
                            scriptMode(newInput[2])
                        }
                    }
                }
            }

            if (newInput[0] == "") {
                println("error: Введите команду!")
            }

            if (newInput[0] != "" && !commandChecker) {
                println("error: Неправильно вводите команду!\nДля справки по командам введите help")
            }

        } while(true)
    }

    private fun scriptMode(str: String) {
        try {
            val file = File(str)
            val scanFile = Scanner(file)
            if (!scanFile.hasNext()) throw IsEmptyException()
            val oldScan = asker.getScan()
            asker.setScan(scanFile)
            asker.setScriptInProgress()
            var newInput: Array<String>

            do {
                newInput = (scanFile.nextLine().trim() + " ").split(" ").toTypedArray()

                if(newInput[0] == "help") {
                    commandManager.help(newInput[1])
                }

                if(newInput[0] == "exit") {
                    commandManager.exit(newInput[1])
                }

                if(newInput[0] == "info") {
                    commandManager.info(newInput[1])
                }

                if (newInput[0] == "add") {
                    commandManager.add(newInput[1])
                }

                if (newInput[0] == "show") {
                    commandManager.show(newInput[1])
                }
                if (newInput[0] == "update") {
                    commandManager.update(newInput[1])
                }

                if (newInput[0] == "remove_by_id") {
                    commandManager.removeById(newInput[1])
                }

                if (newInput[0] == "clear") {
                    commandManager.clear(newInput[1])
                }

                if (newInput[0] == "remove_greater") {
                    commandManager.removeGreater(newInput[1])
                }

                if (newInput[0] == "remove_lower") {
                    commandManager.removeLower(newInput[1])
                }

                if (newInput[0] == "add_if_min") {
                    commandManager.addIfMinOscars(newInput[1])
                }

                if (newInput[0] == "average_of_oscars_count") {
                    commandManager.getAverageOfOscars(newInput[1])
                }

                if (newInput[0] == "count_greater_than_genre") {
                    commandManager.countGenresGreater(newInput[1])
                }

                if (newInput[0] == "print_field_descending_oscars_count") {
                    commandManager.printOscarsCount(newInput[1])
                }

                if (newInput[0] == "save") {
                    commandManager.save(newInput[1])
                }

                if (newInput[0] == "execute_script") {
                    if (newInput[2].isEmpty()) {
                        if (commandManager.executeScript(newInput[1])) {
                            scriptMode(newInput[1])
                            asker.setScriptInProgress()
                        }
                    }else {
                        if (commandManager.executeScriptOptional(newInput[1], newInput[2])) {
                            checkScript(newInput[2])
                        }
                    }
                }
            } while(scanFile.hasNextLine())

            asker.setScan(oldScan)
            asker.setScriptNotInProgress()
        } catch (e: FileNotFoundException) {
            println("error: Файл с таким именем не найден!")
        } catch (e: IsEmptyException) {
            println("error: Файл пустой!")
        }
    }

    private fun checkScript(str: String): Boolean {
        try {
            var exitIsThere = false
            var recursionIsThere = false
            var continueDoingScript = true

            val file = File(str)
            val scanFile = Scanner(file)
            checker.setScan(scanFile)
            if (!scanFile.hasNext()) throw IsEmptyException()
            var newInput: Array<String>

            while (scanFile.hasNextLine() && continueDoingScript) {
                newInput = (scanFile.nextLine().trim() + " ").split(" ").toTypedArray()
                if (newInput[0] == "help" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда help!")
                    continueDoingScript = false
                }

                if (newInput[0] == "info" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда info!")
                    continueDoingScript = false
                }

                if (newInput[0] == "show" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда exit")
                    continueDoingScript = false
                }

                if (newInput[0] == "add" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда add")
                    continueDoingScript = false
                }

                if (newInput[0] == "add" && newInput[1].isEmpty()) {
                    if (checker.checkString()) {
                        println("error: Неправильно введено название кинотеатра!")
                        continueDoingScript = false
                    }
                    if (checker.checkFloatNumber()) {
                        println("error: Неправильно введена координата x!")
                        continueDoingScript = false
                    }
                    if (checker.checkCoordinateY()) {
                        println("error: Неправильно введена координата y!")
                        continueDoingScript = false
                    }
                    if (checker.checkOscarsCount()) {
                        println("error: Неправильно введено количество оскаров!")
                        continueDoingScript = false
                    }
                    if (checker.checkGenre()) {
                        println("error: Неправильно введен жанр фильма!")
                        continueDoingScript = false
                    }
                    if (checker.checkRating()) {
                        println("error: Неправильно введен рейтинг фильма!")
                        continueDoingScript = false
                    }
                    if (checker.checkString()) {
                        println("error: Неправильно введено имя директора!")
                        continueDoingScript = false
                    }
                    if (checker.checkFloatNumber()) {
                        println("error: Неправильно введена координата x!")
                        continueDoingScript = false
                    }
                    if (checker.checkFloatNumber()) {
                        println("error: Неправильно введена координата y!")
                        continueDoingScript = false
                    }
                    if (checker.checkFloatNumber()) {
                        println("error: Неправильно введена координата z!")
                        continueDoingScript = false
                    }
                    if (checker.checkColor()) {
                        println("error: Неправильно введен цвет!")
                        continueDoingScript = false
                    }
                    if (checker.checkBirthday()) {
                        println("error: Неправильно введен день рождения!")
                        continueDoingScript = false
                    }
                }

                if (newInput[0] == "update" && newInput[1].isEmpty()) {
                    println("error: Неправильно введена команда update!")
                    continueDoingScript = false
                }

                if (newInput[0] == "remove_by_id" && newInput[1].isEmpty()) {
                    println("error: Неправильно введена команда remove_by_id!")
                    continueDoingScript = false
                }

                if (newInput[0] == "clear" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда clear!")
                    continueDoingScript = false
                }

                if (newInput[0] == "save" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда save!")
                    continueDoingScript = false
                }

                if (newInput[0] == "execute_script" && newInput[1].isEmpty()) {
                    println("error: Неправильно введена команда execute_script!")
                    continueDoingScript = false
                }

                if (newInput[0] == "execute_script" && newInput[1] == "-delecate" && newInput[2].isEmpty()) {
                    println("error: Неправильно введена команда execute_script -delecate!")
                    continueDoingScript = false
                }

                if (newInput[0] == "execute_script" && newInput[1] != "-delecate" && newInput[1].isNotEmpty()) {
                    recursionIsThere = true
                }

                if (newInput[0] == "execute_script" && newInput[1] == "delecate" && newInput[2].isNotEmpty()) {
                    recursionIsThere = true
                }

                if (newInput[0] == "exit" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда exit!")
                    continueDoingScript = false
                }

                if (newInput[0] == "exit" && newInput[1].isEmpty()) {
                    exitIsThere = true
                }

                if (newInput[0] == "add_if_min" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда add_if_min")
                    continueDoingScript = false
                }

                if (newInput[0] == "remove_greater" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда remove_greater")
                    continueDoingScript = false
                }

                if (newInput[0] == "remove_lower" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда remove_lower")
                    continueDoingScript = false
                }

                if (newInput[0] == "average_of_oscars_count" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда average_of_oscars_count")
                    continueDoingScript = false
                }

                if (newInput[0] == "count_greater_than_genre" && newInput[1].isEmpty()) {
                    println("error: Неправильно введена команда count_greater_than_genre")
                    continueDoingScript = false
                }

                if (newInput[0] == "print_field_descending_oscars_count" && newInput[1].isNotEmpty()) {
                    println("error: Неправильно введена команда print_field_descending_oscars_count")
                    continueDoingScript = false
                }
            }
            if (!continueDoingScript) {
                return continueDoingScript
            }
            if (exitIsThere) {
                if (!asker.askQuestion("В скрипте есть выход из программы, выполнить скрипт?")) continueDoingScript = false
            }

            if (recursionIsThere) {
                if (!asker.askQuestion("В скрипте есть рекурсия, выполнить скрипт?")) continueDoingScript = false
            }
            return continueDoingScript

        } catch (e: FileNotFoundException) {
            println("error: Файл с таким именем не найден!")
            return false
        } catch (e: IsEmptyException) {
            println("error: Файл пустой!")
            return false
        }
    }
}