package collection

enum class MovieGenre {
    COMEDY,
    FANTASY,
    SCIENCE_FICTION;

    companion object {
        fun getEnums(): String {
            var strEnums = ""
            for (genre in values()) {
                strEnums += genre.name + ", "
            }
            return strEnums.substring(0..strEnums.length - 2)
        }
    }
}