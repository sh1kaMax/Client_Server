package utility

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

internal class GenerateIdTest {
    private var fileManager = FileManager("lab5.json", fabrique = Fabrique(Scanner(System.`in`)))
    private val collectionManager = CollectionManager(fileManager.readCollection())
    @Test
    fun testGenerateId() {
        assertEquals(collectionManager.generateId(), collectionManager.getMoviesCollection().last().getId() + 1)
    }
}