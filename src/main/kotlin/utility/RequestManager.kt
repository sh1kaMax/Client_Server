package utility

import collection.*
import commandExecuter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.system.exitProcess

/**
 * Request manager
 *
 * @property collectionManager
 * @property fabrique
 * @constructor Create Request manager
 */
class RequestManager(private var collectionManager: CollectionManager,
                     private var fabrique: Fabrique,
                     private var fileManager: FileManager
                     ) {

    /**
     * make a request on executing help
     *
     */
    fun helpRequest(str: String): CommandResult {
        return if (str.isEmpty()) {
            CommandResult(true, commandExecuter.help())
        }else CommandResult(false, "error: После команды help не должно быть аргументов")
    }

    /**
     * make a request on executing exit
     *
     */
    fun exitRequest(str: String): CommandResult {
        if (str.isEmpty()) {
            exitProcess(0)
        } else return CommandResult(false, "error: После команды exit не должно быть аргументов")
    }


    /**
     * make a request on executing info
     *
     * @return information about collection
     */
    fun infoRequest(str: String): CommandResult {
        return if (str.isEmpty()) {
            CommandResult(true, "Информация о коллекции:\n" +
                    " Тип коллекции: " + collectionManager.getCollectionType() + "\n" +
                    " Размер коллекции: " + collectionManager.getCollectionSize() + "\n" +
                    " Дата и время последнего сохранения: " + collectionManager.getLastSaveTime() + "\n" +
                    " Дата и время последней иницилизации: " + collectionManager.getLastInitTime())
        } else CommandResult(false,"error: После команды exit не должно быть аргументов")
    }

    /**
     * make a request on executing show
     *
     */
    fun showRequest(str: String): CommandResult {
        return if (str.isEmpty()) {
            CommandResult(true, collectionManager.toString())
        } else CommandResult(false, "error: После команды show не должно быть аргументов")
    }

    /**
     * make a request on executing add
     *
     * @param id
     * @param str
     * @return confirmation of the executing the command
     */
    fun addRequest(id: Int?, str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        return if(listOfArguments.size == 14) {
            collectionManager.addObjectToCollection(
                Movie(
                    id ?: collectionManager.generateId(),
                    listOfArguments[0],
                    Coordinates(listOfArguments[1].toFloat(), listOfArguments[2].toInt()),
                    Date.from(Instant.now()),
                    listOfArguments[3].toInt(),
                    MovieGenre.valueOf(listOfArguments[4].uppercase(Locale.getDefault())),
                    MpaaRating.valueOf(listOfArguments[5].uppercase(Locale.getDefault())),
                    Person(listOfArguments[6], ZonedDateTime.of(listOfArguments[7].toInt(), listOfArguments[8].toInt(), listOfArguments[9].toInt(), 0, 0, 0, 0, ZoneId.systemDefault()), Color.valueOf(listOfArguments[10].uppercase(Locale.getDefault())), Location(listOfArguments[11].toFloat(), listOfArguments[12].toFloat(), listOfArguments[13].toFloat()))
                ))
            CommandResult(true, "Кинотеатр создан и добавлен в коллекцию")
        } else CommandResult(false, "error: После команды add не должно быть аргументов")
    }

    /**
     * make a request on executing clear
     *
     * @return confirmation of the executing the command
     */
    fun clearRequest(str: String): CommandResult {
        return if (str.isEmpty()) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(false, "Коллекция пустая")
            } else {
                collectionManager.clearCollection()
                CommandResult(true, "Коллекция очищена")
            }
        } else CommandResult(false, "error: После команды clear не должно быть аргументов")
    }

    /**
     * make a request on executing addIfMinOscarsCount
     *
     * @return confirmation of the executing the command
     */
    fun checkIfMinOscarsCountRequest(str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        return if(listOfArguments.size == 14) {
            if (collectionManager.compareOscarsWithMin(listOfArguments[3].toInt())) {
                addRequest(null, str)
            } else CommandResult(true, "Есть элемент с меньшим количеством оскаров")
        } else CommandResult(false, "error: После команды add_if_min не должно быть аргемнтов")
    }

    /**
     * make a request on executing save
     *
     */
    fun saveRequest(str: String): CommandResult {
        return if (str.isEmpty()) {
            fileManager.writeCollection(collectionManager.getMoviesCollection())
            collectionManager.setLastSaveTime(LocalDateTime.now())
            CommandResult(true, "Коллекция сохранена на файл")
        } else CommandResult(false, "error: После команды save не должно быть аргументов")
    }

    /**
     * make a request on executing countAverageIfOscars
     *
     * @return confirmation of the executing the command
     */
    fun getAverageOscarsRequest(str: String): CommandResult {
        return if (str.isEmpty()) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(true, "Коллекция пустая")
            } else CommandResult(true, "Среднее количество оскаров в кинотаетрах: " + collectionManager.getAverageOfOscars())
        } else CommandResult(false, "error: После команды average_of_oscars_count не должно быть аргументов")
    }

    /**
     * make a request on executing CountGenreGreater
     *
     * @param str
     * @return confirmation of the executing the command
     */
    fun countGreaterGenreRequest(str: String): CommandResult {
        return if (str != "") {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(true, "Коллекция пустая")
            } else {
                try {
                    val genre = MovieGenre.valueOf(str.uppercase(Locale.getDefault()))
                    CommandResult(true, "Количество жанров больше заданного: " + collectionManager.getCountOfGenreGreater(genre))
                } catch (e: IllegalArgumentException) {
                    return CommandResult(false, "error: Такого жанра нету")
                }
            }
        } else CommandResult(false, "error: Неправильный ввод данных")
    }

    /**
     * make a request on executing PrintOscarsCountInDescending
     *
     * @return confirmation of the executing the command
     */
    fun printOscarsCountRequest(str: String): CommandResult {
        return if (str.isEmpty()) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(true, "Коллекция пустая")
            } else CommandResult(true, "Количество оскаров кинотеатров в порядке убывания: " + collectionManager.getOscarsCountsInDescending())
        } else CommandResult(false, "error: После команды print_field_descending_oscars_count не должно быть аргументов")
    }

    /**
     * make a request on executing RemoveById
     *
     * @param str
     * @return confirmation of the executing the command
     */
    fun removeByIdRequest(str: String): CommandResult {
        if (str.isNotEmpty()) {
            try {
                if (collectionManager.getCollectionSize() == 0) {
                    return CommandResult(true, "Коллекция пустая")
                } else {
                    val movie = collectionManager.getById(str.toInt()) ?: return CommandResult(true,"Нету кинотеатра с таким id")
                    collectionManager.removeFromCollection(movie)
                    return CommandResult(true, "Кинотеатр удален")
                }
            } catch (e: NumberFormatException) { return CommandResult(false, "error: Неправильный ввод данных")
            }
        } else return CommandResult(false, "error: После команды remove_by_id должно быть указано id")
    }

    /**
     * make a request on executing RemoveGreaterCountOfOscars
     *
     * @return confirmation of the executing the command
     */
    fun removeGreaterOscarsRequest(str: String): CommandResult {
        return if (str.split(Regex(" ")).toTypedArray().size == 1) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(true, "Коллекция пустая")
            } else {
                CommandResult(true, "Было удалено " + collectionManager.removeGreaterByOscars(str.toInt()) + " кинотеатров, у которых больше оскаров")
            }
        } else CommandResult(false, "error: После команды remove_greater не должно быть аргументов")
    }

    /**
     * make a request on executing RemoveLowerCountOfOscars
     *
     * @return confirmation of the executing the command
     */
    fun removeLowerOscarsRequest(str: String): CommandResult {
        return if (str.split(Regex(" ")).toTypedArray().size == 1) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(true, "Коллекция пустая")
            } else {
                CommandResult(true, "Было удалено " + collectionManager.removeLowerByOscars(str.toInt()) + " кинотеатров, у которых меньше оскаров")
            }
        } else CommandResult(false, "error: После команды remove_lower не должно быть аргументов")
    }

    /**
     * make a request on executing UpdateById
     *
     * @param str
     * @return confirmation of the executing the command
     */
    fun updateByIdRequest(str: String): CommandResult {
        if (str.split(Regex(" ")).toTypedArray().size == 15) {
            try {
                if (collectionManager.getCollectionSize() == 0) {
                    return CommandResult(true, "Коллекция пустая")
                } else {
                    var listOfArguments = str.split(Regex(" ")).toTypedArray()
                    val movie = collectionManager.getById(listOfArguments[0].toInt()) ?: return CommandResult(true, "Нету кинотеатра с таким id")
                    val id = listOfArguments[0].toInt()
                    collectionManager.removeFromCollection(movie)

                    val list = listOfArguments.toMutableList()
                    list.removeAt(0)
                    listOfArguments = list.toTypedArray()

                    return addRequest(id, listOfArguments.joinToString(separator = " "))
                }
            }catch (e: NumberFormatException) { return CommandResult(false, "error: Неправильный ввод данных") }
        } else return CommandResult(false, "error: После команды update должно быть указано id")
    }
}