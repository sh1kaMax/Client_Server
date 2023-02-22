package utility

import commands.Command

class CommandManager(helpCommand: Command, exitCommand: Command, infoCommand: Command, addCommand: Command, showCommand: Command, updateCommand: Command, removeByIdCommand: Command, clearCommand: Command, removeGreaterCommand: Command, removeLowerCommand: Command, addIfMinCommand: Command, averageCommand: Command, countGenreGreaterCommand: Command, printOscarsCountCommand: Command, saveCommand: Command) {

    private var commands = ArrayList<Command>()
    private var helpCommand: Command = helpCommand
    private var exitCommand: Command = exitCommand
    private var infoCommand: Command = infoCommand
    private var addCommand: Command = addCommand
    private var showCommand: Command = showCommand
    private var updateCommand: Command = updateCommand
    private var removeByIdCommand: Command = removeByIdCommand
    private var clearCommand: Command = clearCommand
    private var removeGreaterCommand: Command = removeGreaterCommand
    private var removeLowerCommand: Command = removeLowerCommand
    private var addIfMinCommand: Command = addIfMinCommand
    private var averageCommand: Command = averageCommand
    private var countGenreGreaterCommand: Command = countGenreGreaterCommand
    private var printOscarsCountCommand: Command = printOscarsCountCommand
    private var saveCommand: Command = saveCommand

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
    }

    fun getCommands(): ArrayList<Command> {
        return commands
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
}