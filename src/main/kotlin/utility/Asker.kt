package utility

import collection.*
import exceptions.IsEmptyException
import exceptions.NotInLimitException
import exceptions.NotInTrueFormatException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class Asker(scan: Scanner) {
    private val maxY: Int = 257
    private val minOscarsCount: Long = 1
    private var scan: Scanner = scan
    private var scriptMode = false

    companion object{
        private lateinit var movieName: String
        private lateinit var personName: String
        private lateinit var strX: String
        private var x: Float = 0F
        private lateinit var strY: String
        private var y: Int = 0
        private lateinit var oscarsStr: String
        private var oscars: Int = 0
        private lateinit var genreStr: String
        private lateinit var genre: MovieGenre
        private lateinit var ratingStr: String
        private lateinit var rating: MpaaRating
        private var yearOfBirthday: Int = 0
        private var monthOfBirthday: Int = 0
        private var dayOfBirthday: Int = 0
        private var y2: Float = 0F
        private lateinit var strZ: String
        private var z: Float = 0F
        private lateinit var colorStr: String
        private lateinit var color: Color
    }

    fun setScan(scan: Scanner) {
        this.scan = scan
    }

    fun getScan(): Scanner {
        return scan
    }

    fun setScriptNotInProgress() {
        this.scriptMode = false
    }

    fun setScriptInProgress() {
        this.scriptMode = true
    }

    fun getScriptMode(): Boolean {
        return scriptMode
    }

    fun askForMovieName(): String {
        while(true){
            try {
                if (!scriptMode) print("Введите название кинотеатра:\n>")
                movieName = scan.nextLine().trim()
                if(movieName.equals("")) throw IsEmptyException()
                if(movieName.contains(Regex("[^a-z^A-Z]"))) throw NotInTrueFormatException()
                break
            } catch (e: IsEmptyException) {
                println("error: Название не может быть пустым!")
            } catch (e: NotInTrueFormatException){
                println("error: Название должно содержать только буквы!")
            }
        }
        return movieName
    }

    fun askForFileName(): String{
        while(true){
            try {
                print("Введите название файла:\n>")
                movieName = scan.nextLine().trim()
                if(movieName.equals("")) throw IsEmptyException()
                if(movieName.contains(Regex("[^a-z^A-Z0-9]"))) throw NotInTrueFormatException()
                movieName += ".json"
                break
            } catch (e: IsEmptyException) {
                println("error: Название не может быть пустым!")
            } catch (e: NotInTrueFormatException){
                println("error: Название должно содержать только буквы!")
            }
        }
        return movieName
    }

    fun askForDirectorName(): String {
        while(true){
            try {
                if (!scriptMode) print("Введите имя директора:\n>")
                personName = scan.nextLine().trim()
                if (personName.equals("")) throw IsEmptyException()
                if(personName.contains(Regex("[^a-z^A-Z]"))) throw NotInTrueFormatException()
                break
            } catch (e: IsEmptyException) {
                println("error: Имя не может быть пустым!")
            } catch (e: NotInTrueFormatException) {
                println("error: Имя должно содержать только буквы!")
            }
        }
        return personName
    }

    fun askForX(): Float {
        while(true){
            try {
                if (!scriptMode) print("Введите координату x:\n>")
                strX = scan.nextLine().trim()
                if (strX.equals("")) throw IsEmptyException()
                x = strX.toFloat()
                break
            } catch (e: NumberFormatException) {
                println("error: Координата x должна быть числом!")
            } catch (e: IsEmptyException) {
                println("error: Координата не может быть пустым!")
            }
        }
        return x
    }

    fun askForY(): Int {
        while(true){
            try {
                if (!scriptMode) print("Введите координату y:\n>")
                strY = scan.nextLine().trim()
                if (strY.equals("")) throw IsEmptyException()
                y = strY.toInt()
                if(y > maxY) throw NotInLimitException()
                break
            } catch (e: NumberFormatException) {
                println("error: Координата y должна быть числом!")
            } catch (e: IsEmptyException) {
                println("error: Поле координат не может быть пустым!")
            }catch (e: NotInLimitException) {
                println("error: Максимальное значение y: 257!")
            }
        }
        return y
    }

    fun askForZ(): Float {
        while(true){
            try {
                if (!scriptMode) print("Введите координату z:\n>")
                strZ = scan.nextLine().trim()
                if (strZ.equals("")) throw IsEmptyException()
                z = strZ.toFloat()
                break
            } catch (e: NumberFormatException) {
                println("error: Координата x должна быть числом!")
            } catch (e: IsEmptyException) {
                println("error: Координата не может быть пустым!")
            }
        }
        return z
    }

    fun askForCoordinates(): Coordinates {
        x = askForX()
        y = askForY()
        return Coordinates(x, y)
    }

    fun askForLocation(): Location {
        x = askForX()
        y2 = askForY().toFloat()
        z = askForZ()
        return Location(x, y2, z)
    }

    fun askForOscarsCount(): Int {
        while (true) {
            try {
                if (!scriptMode) print("Введите сколько оскаров получил кинотеатр:\n>")
                oscarsStr = scan.nextLine().trim()
                if (oscarsStr.equals("")) throw IsEmptyException()
                oscars = oscarsStr.toInt()
                if (oscars < minOscarsCount) throw NotInLimitException()
                break
            } catch (e: IsEmptyException) {
                println("error: Количетво не может быть пустым!")
            } catch (e: NotInLimitException) {
                println("error: Минимальное количество оскаров 1!")
            } catch (e: NumberFormatException) {
                println("error: Количество должно быть числом")
            }
        }
        return oscars
    }

    fun askForGenre(): MovieGenre {
        while (true) {
            try {
                if (!scriptMode) {
                    println("Список жанров - " + MovieGenre.getEnums())
                    print("Введите жанр фильма:\n>")
                }
                genreStr = scan.nextLine().trim()
                if (genreStr.equals("")) throw IsEmptyException()
                genre = MovieGenre.valueOf(genreStr.uppercase(Locale.getDefault()))
                break
            } catch (e: IsEmptyException) {
                println("error: Жанр не может быть пустым!")
            } catch (e: IllegalArgumentException) {
                println("error: Такого жанра нету в списке!")
            }
        }
        return genre
    }

    fun askForRating(): MpaaRating {
        while (true) {
            try {
                if (!scriptMode) {
                    println("Список рейтингов - " + MpaaRating.getEnums())
                    print("Введите рейтинг:\n>")
                }
                ratingStr = scan.nextLine().trim()
                if (ratingStr.equals("")) throw IsEmptyException()
                rating = MpaaRating.valueOf(ratingStr.uppercase(Locale.getDefault()))
                break
            }catch (e: IsEmptyException){
                println("error: Рейтинг не должен быть пустым!")
            }catch (e: IllegalArgumentException){
                println("error: Такого рейтинга нету в списке!")
            }
        }
        return rating
    }

    fun askForEyeColor(): Color {
        while (true) {
            try {
                if (!scriptMode) {
                    println("Список цветов - " + Color.getEnums())
                    print("Введите цвет глаз директора:\n>")
                }
                colorStr = scan.nextLine().trim()
                if (colorStr.equals("")) throw IsEmptyException()
                color = Color.valueOf(colorStr.uppercase(Locale.getDefault()))
                break
            } catch (e: IsEmptyException) {
                println("error: Жанр не может быть пустым!")
            } catch (e: IllegalArgumentException) {
                println("error: Такого жанра нету в списке!")
            }
        }
        return color
    }

    fun askForPerson(): Person {
        personName = askForDirectorName()
        x = askForX()
        y2 = askForY().toFloat()
        z = askForZ()
        color = askForEyeColor()
        while (true){
            try {
                if (!scriptMode) print("Введите день рождения директора через пробел(например 1 3 2004 - это первое марта 2004 годп)\n>")
                dayOfBirthday = scan.next().trim().toInt()
                monthOfBirthday = scan.next().trim().toInt()
                yearOfBirthday = scan.next().trim().toInt()
                if (dayOfBirthday.equals("") || monthOfBirthday.equals("") || yearOfBirthday.equals("")) throw IsEmptyException()
                if (dayOfBirthday !in 1..31 || monthOfBirthday !in 1..12 || yearOfBirthday !in 1923..2023) throw NotInLimitException()
                break
            }catch (e: IsEmptyException){
                println("error: Все значения должны быть заполнены!")
            }catch (e: NumberFormatException){
                println("error: Все значения должны быть числами!")
            }catch (e: NotInLimitException){
                println("error: Неправильные значения!")
            }
        }
        return Person(personName, ZonedDateTime.of(yearOfBirthday, monthOfBirthday, dayOfBirthday, 0, 0, 0, 0, ZoneId.systemDefault()), color, Location(x, y2, z))
    }

    fun askQuestion(argument: String): Boolean {
        val question = argument + " (yes/no):\n>"
        var answer: String
        while (true) {
            try {
                print(question)
                answer = scan.nextLine().trim()
                if (!answer.equals("yes") && !answer.equals("no")) throw NotInLimitException()
                break
            }catch (e: NotInLimitException) {
                println("error: Ответ должен быть yes или no")
            }
        }
        return answer.equals("yes")
    }

    fun askForOscarsToRemoveGreater(): Int {
        while (true) {
            try {
                print("Введите количество оскаров, больше которого не может быть у элементов коллекции:\n>")
                oscarsStr = scan.nextLine().trim()
                if (oscarsStr.equals("")) throw IsEmptyException()
                oscars = oscarsStr.toInt()
                if (oscars < minOscarsCount) throw NotInLimitException()
                break
            } catch (e: IsEmptyException) {
                println("error: Количетво не может быть пустым!")
            } catch (e: NumberFormatException) {
                println("error: Количество должно быть числом")
            }
        }
        return oscars
    }

    fun askForOscarsToRemoveLower(): Int {
        while (true) {
            try {
                print("Введите количество оскаров, меньше которого не может быть у элементов коллекции:\n>")
                oscarsStr = scan.nextLine().trim()
                if (oscarsStr.equals("")) throw IsEmptyException()
                oscars = oscarsStr.toInt()
                if (oscars < minOscarsCount) throw NotInLimitException()
                break
            } catch (e: IsEmptyException) {
                println("error: Количетво не может быть пустым!")
            } catch (e: NumberFormatException) {
                println("error: Количество должно быть числом")
            }
        }
        return oscars
    }
}