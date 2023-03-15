package collection

/**
 * Enum with colors of eyes
 */
enum class Color {
    GREEN,
    YELLOW,
    ORANGE,
    WHITE,
    BROWN;

    companion object {
        /**
         * @return String with enums of this class
         */
        fun getEnums(): String {
            var strEnums = ""
            for (genre in values()) {
                strEnums += genre.name + ", "
            }
            return strEnums.substring(0..strEnums.length - 2)
        }
    }
}