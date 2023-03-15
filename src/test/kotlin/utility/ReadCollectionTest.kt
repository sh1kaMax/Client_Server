package utility

import collection.Movie
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.test.assertEquals

class ReadCollectionTest {
    private val fileManager = FileManager("lab5.json", fabrique = Fabrique(Scanner(System.`in`)))
    private val scanFile = Scanner(File("lab5.json"))

    @Test
    fun testReadCollection() {
        assertEquals(TreeSet(Json.decodeFromString<List<Movie>>(scanFile.nextLine())), fileManager.readCollection())
    }
}