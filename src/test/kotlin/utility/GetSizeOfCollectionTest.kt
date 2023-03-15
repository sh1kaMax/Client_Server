package utility

import collection.*
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.test.assertEquals

class GetSizeOfCollectionTest {
private var fileManager = FileManager("lab5.json", fabrique = Fabrique(Scanner(System.`in`)))
    private val collectionManager = CollectionManager(fileManager.readCollection())

    @Test
    fun testGetSizeOfCollection() {
        collectionManager.clearCollection()
        assertEquals(0, collectionManager.getCollectionSize())

        collectionManager.addObjectToCollection(Movie(collectionManager.generateId(),
            "LondonMall",
            Coordinates(123F, 123),
            Date.from(Instant.now()),
            123,
            MovieGenre.COMEDY,
            MpaaRating.G,
            Person("Max",
                ZonedDateTime.of(2004, 3, 1, 0, 0, 0, 0, ZoneId.systemDefault()),
                Color.GREEN,
                Location(123F, 123F, 123F)
                )
            ))

        assertEquals(1, collectionManager.getCollectionSize())

        collectionManager.addObjectToCollection(Movie(collectionManager.generateId(),
            "LondonMall",
            Coordinates(123F, 123),
            Date.from(Instant.now()),
            123,
            MovieGenre.COMEDY,
            MpaaRating.G,
            Person("Max",
                ZonedDateTime.of(2004, 3, 1, 0, 0, 0, 0, ZoneId.systemDefault()),
                Color.GREEN,
                Location(123F, 123F, 123F)
            )
        ))

        assertEquals(2, collectionManager.getCollectionSize())
    }
}