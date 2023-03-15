package collection

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

internal class MovieTest {

    @Test
    fun testMovie() {
        val movie = Movie(
            1,
            "LondonMall",
            Coordinates(123F, 123),
            Date.from(Instant.now()),
            12,
            MovieGenre.COMEDY,
            MpaaRating.G,
            Person("Max",
                ZonedDateTime.of(2004, 3, 1, 0, 0, 0, 0, ZoneId.systemDefault()),
                Color.BROWN,
                Location(123F,123F,123F))
        )
        assertAll("movie",
                    Executable { assertEquals("LondonMall", movie.getName())},
                    Executable { assertEquals(MovieGenre.COMEDY, movie.getGenre())},
                    Executable { assertEquals(MpaaRating.G, movie.getMpaaRating()) }
            )
    }
}