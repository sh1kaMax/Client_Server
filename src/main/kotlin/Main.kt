
import utility.*
import java.util.*

val scan = Scanner(System.`in`)
val fabrique: Fabrique = Fabrique(scan)
val fileManager: FileManager = FileManager(fabrique.askForFileName(), fabrique)
val collectionManager = CollectionManager(fileManager.readCollection())
val commandExecuter = CommandExecuter(fabrique)
val requestManager = RequestManager(collectionManager, fabrique, fileManager)

/**
 * run the program
 *
 */

fun main(args: Array<String>) {
        val test = ConsoleManager(scan, commandExecuter, fabrique)
        test.run()
}
