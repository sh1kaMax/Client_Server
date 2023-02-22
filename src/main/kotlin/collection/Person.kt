package collection
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import java.time.ZonedDateTime

object ZonedDateSerializer : KSerializer<ZonedDateTime> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ZonedDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: ZonedDateTime) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): ZonedDateTime {
        val string = decoder.decodeString()
        return ZonedDateTime.parse(string)
    }
}

@Serializable
data class Person(private var name: String,
                  @Serializable(ZonedDateSerializer::class)
                  private var birthday: ZonedDateTime,
                  private var eyeColor: Color,
                  private var location: Location
                  ) {

    fun getName(): String {
        return name
    }

    fun getBirthday(): ZonedDateTime {
        return birthday
    }

    fun getEyeColor(): Color {
        return eyeColor
    }

    fun getLocation(): Location {
        return location
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (name != other.name) return false
        if (birthday != other.birthday) return false
        if (eyeColor != other.eyeColor) return false
        if (location != other.location) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + birthday.hashCode()
        result = 31 * result + eyeColor.hashCode()
        result = 31 * result + location.hashCode()
        return result
    }

    override fun toString(): String {
        return "Person(name='$name', birthday=$birthday, eyeColor=$eyeColor, location=$location)"
    }
}