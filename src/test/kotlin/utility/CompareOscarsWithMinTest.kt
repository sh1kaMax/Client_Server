package utility

import collection.*
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


class CompareOscarsWithMinTest {
    private var fileManager = FileManager("lab5.json", fabrique = Fabrique(Scanner(System.`in`)))
    private val collectionManager = CollectionManager(fileManager.readCollection())

    @Test
    fun testCompareWithMin() {
        collectionManager.clearCollection()
        collectionManager.addObjectToCollection(
            Movie(collectionManager.generateId(),
            "LondonMall",
            Coordinates(123F, 123),
            Date.from(Instant.now()),
            12,
            MovieGenre.COMEDY,
            MpaaRating.G,
            Person("Max",
                ZonedDateTime.of(2004, 3, 1, 0, 0, 0, 0, ZoneId.systemDefault()),
                Color.GREEN,
                Location(123F, 123F, 123F)
            )))
        collectionManager.addObjectToCollection(
            Movie(collectionManager.generateId(),
                "LondonMall",
                Coordinates(123F, 123),
                Date.from(Instant.now()),
                6,
                MovieGenre.COMEDY,
                MpaaRating.G,
                Person("Max",
                    ZonedDateTime.of(2004, 3, 1, 0, 0, 0, 0, ZoneId.systemDefault()),
                    Color.GREEN,
                    Location(123F, 123F, 123F)
                )))
        assertAll("compareWithMin",
                Executable { assertEquals(false, collectionManager.compareOscarsWithMin(7)) },
                Executable { assertEquals(true, collectionManager.compareOscarsWithMin(1)) },
                Executable { assertEquals(false, collectionManager.compareOscarsWithMin(15)) }
            )
    }
}