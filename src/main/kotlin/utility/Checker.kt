package utility

import java.util.*

class Checker(private var scan: Scanner) {

    fun checkString(): Boolean {
        val string = scan.nextLine().trim()
        if (string.equals("")) return true
        if (string.contains(Regex("[^a-z^A-Z]"))) return true
        return false
    }

    fun checkFloatNumber(): Boolean {
        try {
            val floatNumber = scan.nextLine().trim()
            if (floatNumber.equals("")) return true
            floatNumber.toFloat()
        } catch (e: NumberFormatException) {
            return true
        }
        return false
    }

    fun checkIntNumber(): Boolean {
        try {
            val intNumber = scan.nextLine().trim()
            if (intNumber.equals("")) return true
            intNumber.toInt()
        } catch (e: NumberFormatException) {
            return true
        }
        return false
    }


}