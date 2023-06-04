
import utility.ClientManager
import utility.ConsoleManager
import utility.Fabrique
import java.util.*

val scan = Scanner(System.`in`)
val fabrique: Fabrique = Fabrique(scan)
val clientManager = ClientManager(fabrique)
val app = ConsoleManager(scan, clientManager, fabrique)
/**
 * run the program
 *
 */

fun main(args: Array<String>) {
        app.run()
}
