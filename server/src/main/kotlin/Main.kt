
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import utility.*
import java.util.*

val scan = Scanner(System.`in`)
val fileManager: FileManager = FileManager()
val dataBaseHandler = DataBaseHandler()
val collectionManager = CollectionManager()
val commandExecuter = CommandExecuter()
val requestManager = RequestManager(collectionManager, fileManager)
val serverThread = ServerThread(scan)
val logger: Logger = LogManager.getLogger()
val authManager = AuthManager()
val hasher = PasswordHasher()
val app = ServerManager(scan, commandExecuter)


/**
 * run the program
 *
 */

fun main(args: Array<String>) {

//        dataBaseHandler.connectToDataBase()
        serverThread.start()
        app.run()

}
