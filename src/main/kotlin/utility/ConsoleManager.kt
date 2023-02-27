package utility

import asker
import exceptions.IsEmptyException
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class ConsoleManager(commandManager: CommandManager,
                     scan: Scanner,
                     checker: Checker
) {
    private var commandManager: CommandManager
    private var scan: Scanner
    private var checker: Checker

    init {
        this.commandManager = commandManager
        this.scan = scan
        this.checker = checker
    }

    fun run() {
        println("Начало работы программы, для справки по командам вызовите help")
        interactiveMode()
    }

    private fun interactiveMode() {
        var newInput: Array<String>
        do {
            newInput = (scan.nextLine().trim() + " ").split(" ").toTypedArray()

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
                    }
                }else {
                    if (commandManager.executeScriptOptional(newInput[1], newInput[2])) {
                        println("Здаров")
                    }
                }
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
                            println("Здаров")
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

    private fun checkScript(str: String) {
        try {
            var exitIsThere = false
            var recursionIsThere = false
            var continueDoingScript = true

            val file = File(str)
            val scanFile = Scanner(file)
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
                    if (checker.checkIntNumber()) {
                        println("error: Неправильно введена координата y!")
                        continueDoingScript = false
                    }
                    if (checker.checkIntNumber()) {
                        println("error: Неправильно введено количество оскаров!")
                        continueDoingScript = false
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            println("error: Файл с таким именем не найден!")
        } catch (e: IsEmptyException) {
            println("error: Файл пустой!")
        }
    }
}