
import utility.*
import java.util.*

val scan = Scanner(System.`in`)
val asker: Asker = Asker(scan)
val fileManager: FileManager = FileManager(asker.askForFileName())
val collectionManager = CollectionManager(fileManager)
val commandExecuter = CommandExecuter()
val requestManager = RequestManager(collectionManager, asker)

fun main() {
    val test = ConsoleManager(scan, commandExecuter)
    test.run()
}