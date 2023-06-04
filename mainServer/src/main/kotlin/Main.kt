import utility.ServerManager
import utility.ServerThread
import java.util.*

val scan = Scanner(System.`in`)
val mainServer = ServerManager()
val mainServerThread = ServerThread(scan)



fun main(args: Array<String>) {
    mainServerThread.start()
    mainServer.run()
}