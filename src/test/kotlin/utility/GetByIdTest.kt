package utility

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.util.*
import kotlin.test.assertEquals

class GetByIdTest {
private var fileManager = FileManager("lab5.json", fabrique = Fabrique(Scanner(System.`in`)))
    private val collectionManager = CollectionManager(fileManager.readCollection())

    @Test
    fun testGetById() {
        assertAll("getCollectionManager",
            Executable { assertEquals(collectionManager.getMoviesCollection().find { movie -> movie.getId() == 2 }, collectionManager.getById(2)) },
            Executable { assertEquals(collectionManager.getMoviesCollection().find { movie -> movie.getId() == 7 }, collectionManager.getById(7))},
            Executable { assertEquals(collectionManager.getMoviesCollection().find { movie -> movie.getId() == 123 }, collectionManager.getById(123))}
        )
    }
}