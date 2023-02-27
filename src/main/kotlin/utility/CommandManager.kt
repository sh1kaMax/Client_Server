package utility

import commands.Command
import commands.ExecuteScript

class CommandManager(private var helpCommand: Command,
                     private var exitCommand: Command,
                     private var infoCommand: Command,
                     private var addCommand: Command,
                     private var showCommand: Command,
                     private var updateCommand: Command,
                     private var removeByIdCommand: Command,
                     private var clearCommand: Command,
                     private var removeGreaterCommand: Command,
                     private var removeLowerCommand: Command,
                     private var addIfMinCommand: Command,
                     private var averageCommand: Command,
                     private var countGenreGreaterCommand: Command,
                     private var printOscarsCountCommand: Command,
                     private var saveCommand: Command,
                     private var executeScriptCommand: ExecuteScript
) {

    private var commands = ArrayList<Command>()

    init {
        commands.add(helpCommand)
        commands.add(exitCommand)
        commands.add(infoCommand)
        commands.add(addCommand)
        commands.add(showCommand)
        commands.add(updateCommand)
        commands.add(removeByIdCommand)
        commands.add(clearCommand)
        commands.add(removeGreaterCommand)
        commands.add(removeLowerCommand)
        commands.add(addIfMinCommand)
        commands.add(averageCommand)
        commands.add(countGenreGreaterCommand)
        commands.add(printOscarsCountCommand)
        commands.add(saveCommand)
        commands.add(executeScriptCommand)
    }

    fun help(str: String){
        if(helpCommand.execute(str)){
            for (command in commands) {
                println(command.getName() + ": " + command.getExplanationOfCommand())
            }
        }
    }

    fun exit(str: String){
        exitCommand.execute(str)
    }

    fun info(str: String){
        infoCommand.execute(str)
    }

    fun add(str: String){
        addCommand.execute(str)
    }

    fun show(str: String){
        showCommand.execute(str)
    }

    fun update(str: String){
        updateCommand.execute(str)
    }

    fun removeById(str: String){
        removeByIdCommand.execute(str)
    }

    fun clear(str: String){
        clearCommand.execute(str)
    }

    fun removeGreater(str: String){
        removeGreaterCommand.execute(str)
    }

    fun removeLower(str: String){
        removeLowerCommand.execute(str)
    }

    fun addIfMinOscars(str: String){
        addIfMinCommand.execute(str)
    }

    fun getAverageOfOscars(str: String) {
        averageCommand.execute(str)
    }

    fun countGenresGreater(str: String) {
        countGenreGreaterCommand.execute(str)
    }

    fun printOscarsCount(str: String) {
        printOscarsCountCommand.execute(str)
    }

    fun save(str: String) {
        saveCommand.execute(str)
    }

    fun executeScript(str: String): Boolean {
        return executeScriptCommand.execute(str)
    }

    fun executeScriptOptional(str1: String, str2: String): Boolean {
        return executeScriptCommand.executeOptional(str1, str2)
    }
 }