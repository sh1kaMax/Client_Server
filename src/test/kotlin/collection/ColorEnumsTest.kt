package collection

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertNotNull

class ColorEnumsTest {


    @ParameterizedTest
    @EnumSource(Color::class)
    fun testColorEnums(color: Color) {
        assertNotNull(color)
    }
}