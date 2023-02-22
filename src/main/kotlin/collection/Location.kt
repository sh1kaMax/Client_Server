package collection

import kotlinx.serialization.Serializable

@Serializable
data class Location(private var x: Float,
                    private var y: Float,
                    private var z: Float
                    ) {

    fun getX(): Float{
        return x
    }

    fun getY(): Float {
        return y
    }

    fun getZ(): Float {
        return z
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Location

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    override fun toString(): String {
        return "Location(x=$x, y=$y, z=$z)"
    }
}