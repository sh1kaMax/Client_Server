
import commands.*
import utility.*
import java.util.*

val scan = Scanner(System.`in`)
val asker: Asker = Asker(scan)
val fileManager: FileManager = FileManager(asker.askForFileName())
val collectionManager: CollectionManager = CollectionManager(fileManager)

val commandManager: CommandManager = CommandManager(Help(), Exit(), Info(collectionManager), Add(collectionManager, asker), Show(collectionManager), UpdateById(collectionManager, asker), RemoveById(collectionManager), Clear(collectionManager), RemoveGreaterCountOfOscars(collectionManager, asker), RemoveLowerCountOfOscars(collectionManager, asker), AddIfMinOscarsCount(collectionManager, asker), CountAvarageOfOscars(collectionManager), CountGenresGreater(collectionManager), PrintOscarsCountsInDescending(collectionManager), Save(collectionManager))

fun main(args: Array<String>){
    val test = ConsoleManager(commandManager, scan)
    test.run()
}