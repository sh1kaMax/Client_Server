package collection

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(private var x: Float,
                       private var y: Int
                       ) {

    fun getX(): Float{
        return x
    }

    fun getY(): Int{
        return y
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Coordinates) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "Coordinates(x=$x, y=$y)"
    }
}