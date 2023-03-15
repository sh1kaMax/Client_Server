package collection

/**
 * Enum with ratings of movie
 */
enum class MpaaRating {
    G,
    PG_13,
    R,
    NC_17;

    companion object {
        /**
         * @return String with enums of this class
         */
        fun getEnums(): String {
            var strEnums = ""
            for (rating in values()) {
                strEnums += rating.name + ", "
            }
            return strEnums.substring(0..strEnums.length - 2)
        }
    }
}