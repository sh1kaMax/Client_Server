package utility

import collection.Movie
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class GetCollectionTypeTest {
    private var fileManager = FileManager("lab5.json", fabrique = Fabrique(Scanner(System.`in`)))
    private val testTreeSetCollection = TreeSet<Movie>()
    private val collectionManager = CollectionManager(fileManager.readCollection())

    @Test
    fun testGetCollectionType() {
        assertEquals(testTreeSetCollection.javaClass.typeName, collectionManager.getCollectionType())
    }
}