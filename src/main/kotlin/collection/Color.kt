package collection

enum class Color {
    GREEN,
    YELLOW,
    ORANGE,
    WHITE,
    BROWN;

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