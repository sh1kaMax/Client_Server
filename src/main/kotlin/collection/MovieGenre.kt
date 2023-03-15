package collection

/**
 * Enum with genre of movie
 */
enum class MovieGenre {
    COMEDY,
    FANTASY,
    SCIENCE_FICTION;

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