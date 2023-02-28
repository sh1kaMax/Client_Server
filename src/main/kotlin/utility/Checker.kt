package utility

import collection.Color
import collection.MovieGenre
import collection.MpaaRating
import exceptions.NotInLimitException
import java.util.*

class Checker(private var scan: Scanner) {

    fun setScan(scan: Scanner) {
        this.scan = scan
    }
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
            return false
        } catch (e: NumberFormatException) {
            return true
        }
    }

    fun checkCoordinateY(): Boolean {
        try {
            val intY = scan.nextLine().trim()
            if (intY.equals("")) return true
            intY.toInt()
            if (intY.toInt() > 257) return true
            return false
        } catch (e: NumberFormatException) {
            return true
        }
    }

    fun checkOscarsCount(): Boolean {
        try {
            val oscarsCount = scan.nextLine().trim()
            if (oscarsCount.equals("")) return true
            oscarsCount.toInt()
            if (oscarsCount.toInt() < 1) return true
            return false
        } catch (e: NumberFormatException) {
            return true
        }
    }

    fun checkGenre(): Boolean {
        try {
            val genreStr = scan.nextLine().trim()
            if (genreStr.equals("")) return true
            val genre = MovieGenre.valueOf(genreStr.uppercase(Locale.getDefault()))
            return false
        }catch (e: IllegalArgumentException) {
            return true
        }
    }

    fun checkRating(): Boolean {
        try {
            val ratingStr = scan.nextLine().trim()
            if (ratingStr.equals("")) return true
            val rating = MpaaRating.valueOf(ratingStr.uppercase(Locale.getDefault()))
            return false
        }catch (e: IllegalArgumentException) {
            return true
        }
    }

    fun checkColor(): Boolean {
        try {
            val colorStr = scan.nextLine().trim()
            if (colorStr.equals("")) return true
            val rating = Color.valueOf(colorStr.uppercase(Locale.getDefault()))
            return false
        }catch (e: IllegalArgumentException) {
            return true
        }
    }

    fun checkBirthday(): Boolean {
        try {
            val dayOfBirthday = scan.next().trim().toInt()
            val monthOfBirthday = scan.next().trim().toInt()
            val yearOfBirthday = scan.next().trim().toInt()
            if (dayOfBirthday.equals("") || monthOfBirthday.equals("") || yearOfBirthday.equals("")) return true
            if (dayOfBirthday !in 1..31 || monthOfBirthday !in 1..12 || yearOfBirthday !in 1923..2023) throw NotInLimitException()
            return false
        }catch (e: NotInLimitException) {
            return true
        }
    }

    fun checkAnswer(): String {
        return scan.nextLine().trim()
    }
}