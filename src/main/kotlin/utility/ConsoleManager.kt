package utility

import java.util.*

class ConsoleManager(commandManager: CommandManager, scan: Scanner) {
    private var commandManager: CommandManager
    private var scan: Scanner

    init {
        this.commandManager = commandManager
        this.scan = scan
    }

    fun run() {
        var newInput: Array<String>
        println("Начало работы программы, для справки по командам вызовите help")
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
        } while(true)
    }
}