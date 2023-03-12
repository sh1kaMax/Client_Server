package utility

import collection.*
import exceptions.IsEmptyException
import exceptions.NotInLimitException
import exceptions.NotInTrueFormatException
import java.io.FileNotFoundException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class Asker(private var scan: Scanner) {
    private val maxY: Int = 257
    private val minOscarsCount: Long = 1
    private var scriptMode = false

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

    fun askForMovieName(): String {
        var movieName: String
        while(true){
            try {
                if (!scriptMode) print("Введите название кинотеатра:\n>")
                movieName = scan.nextLine().trim()
                if(movieName == "") throw IsEmptyException()
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
        var fileName: String
        while(true){
            try {
                print("Введите название файла:\n>")
                fileName = scan.nextLine().trim()
                if(fileName == "") throw IsEmptyException()
                if(fileName.contains(Regex("[^a-z^A-Z0-9]"))) throw NotInTrueFormatException()
                fileName += ".json"
                break
            } catch (e: IsEmptyException) {
                println("error: Название не может быть пустым!")
            } catch (e: NotInTrueFormatException){
                println("error: Название должно содержать только буквы!")
            } catch (e: FileNotFoundException) {
                println("error: Файла с таким названием нету!")
            }
        }
        return fileName
    }

    private fun askForDirectorName(): String {
        var personName: String
        while(true){
            try {
                if (!scriptMode) print("Введите имя директора:\n>")
                personName = scan.nextLine().trim()
                if (personName == "") throw IsEmptyException()
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

    private fun askForX(): Float {
        var strX: String
        var x: Float
        while(true){
            try {
                if (!scriptMode) print("Введите координату x:\n>")
                strX = scan.nextLine().trim()
                if (strX == "") throw IsEmptyException()
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

    private fun askForY(): Int {
        var strY: String
        var y: Int
        while(true){
            try {
                if (!scriptMode) print("Введите координату y:\n>")
                strY = scan.nextLine().trim()
                if (strY == "") throw IsEmptyException()
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

    private fun askForZ(): Float {
        var strZ: String
        var z: Float
        while(true){
            try {
                if (!scriptMode) print("Введите координату z:\n>")
                strZ = scan.nextLine().trim()
                if (strZ == "") throw IsEmptyException()
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
        val x = askForX()
        val y = askForY()
        return Coordinates(x, y)
    }

    fun askForOscarsCount(): Int {
        var oscarsStr: String
        var oscars: Int
        while (true) {
            try {
                if (!scriptMode) print("Введите сколько оскаров получил кинотеатр:\n>")
                oscarsStr = scan.nextLine().trim()
                if (oscarsStr == "") throw IsEmptyException()
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
        var genreStr: String
        var genre: MovieGenre
        while (true) {
            try {
                if (!scriptMode) {
                    println("Список жанров - " + MovieGenre.getEnums())
                    print("Введите жанр фильма:\n>")
                }
                genreStr = scan.nextLine().trim()
                if (genreStr == "") throw IsEmptyException()
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
        var ratingStr: String
        var rating: MpaaRating
        while (true) {
            try {
                if (!scriptMode) {
                    println("Список рейтингов - " + MpaaRating.getEnums())
                    print("Введите рейтинг:\n>")
                }
                ratingStr = scan.nextLine().trim()
                if (ratingStr == "") throw IsEmptyException()
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

    private fun askForEyeColor(): Color {
        var colorStr: String
        var color: Color
        while (true) {
            try {
                if (!scriptMode) {
                    println("Список цветов - " + Color.getEnums())
                    print("Введите цвет глаз директора:\n>")
                }
                colorStr = scan.nextLine().trim()
                if (colorStr == "") throw IsEmptyException()
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
        var birthday: Array<String>
        val personName = askForDirectorName()
        val x = askForX()
        val y2 = askForY().toFloat()
        val z = askForZ()
        val color = askForEyeColor()
        while (true){
            try {
                if (!scriptMode) print("Введите день рождения директора через пробел(например 1 3 2004 - это первое марта 2004 годп)\n>")
                birthday = (scan.nextLine().trim() + " ").split(Regex(" ")).toTypedArray()
                if (birthday[0] == "" || birthday[1] == "" || birthday[2] == "") throw IsEmptyException()
                if (birthday[0].toInt() !in 1..31 || birthday[1].toInt() !in 1..12 || birthday[2].toInt() !in 1923..2023) throw NotInLimitException()
                break
            }catch (e: IsEmptyException){
                println("error: Все значения должны быть заполнены!")
            }catch (e: NumberFormatException){
                println("error: Все значения должны быть числами!")
            }catch (e: NotInLimitException){
                println("error: Неправильные значения!")
            }
        }
        return Person(personName, ZonedDateTime.of(birthday[2].toInt(), birthday[1].toInt(), birthday[0].toInt(), 0, 0, 0, 0, ZoneId.systemDefault()), color, Location(x, y2, z))
    }

    fun askQuestion(argument: String): Boolean {
        val question = "$argument (yes/no):\n>"
        var answer: String
        while (true) {
            try {
                print(question)
                answer = scan.nextLine().trim()
                if (answer != "yes" && answer != "no") throw NotInLimitException()
                break
            }catch (e: NotInLimitException) {
                println("error: Ответ должен быть yes или no")
            }
        }
        return answer == "yes"
    }

    fun askForOscarsToRemoveGreater(): Int {
        var oscarsStr: String
        var oscars: Int
        while (true) {
            try {
                print("Введите количество оскаров, больше которого не может быть у элементов коллекции:\n>")
                oscarsStr = scan.nextLine().trim()
                if (oscarsStr == "") throw IsEmptyException()
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
        var oscarsStr: String
        var oscars: Int
        while (true) {
            try {
                print("Введите количество оскаров, меньше которого не может быть у элементов коллекции:\n>")
                oscarsStr = scan.nextLine().trim()
                if (oscarsStr == "") throw IsEmptyException()
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