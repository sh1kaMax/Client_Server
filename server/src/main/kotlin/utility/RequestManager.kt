package utility

import app
import collection.*
import commandExecuter
import dataBaseHandler
import logger
import java.io.IOException
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
                     private var fileManager: FileManager
                     ) {

    /**
     * make a request on executing help
     *
     */
    fun helpRequest(str: String): CommandResult {
        return if (str.split(Regex(" ")).toTypedArray().size == 1) {
            CommandResult(StateOfResponse.GOOD, commandExecuter.help())
        }else {
            println(str.split(Regex(" ")).toTypedArray().joinToString())
            CommandResult(StateOfResponse.ERROR, "error: После команды help не должно быть аргументов")}
    }

    /**
     * make a request on executing exit
     *
     */
    fun exitClientRequest(str: String): CommandResult {
        return if (str.split(Regex(" ")).toTypedArray().size == 1) {
            CommandResult(StateOfResponse.CLIENT_EXIT, "Выключение клиентского модуля")
        } else CommandResult(StateOfResponse.ERROR, "error: После команды exit не должно быть аргументов")
    }

    fun exitServerRequest(str: String){
        saveRequest("")
        logger.info("Завершение работы сервера...")
        try {
            app.getServerSocket()!!.close()
        } catch (e: IOException) {
            logger.error("Ошибка при завершении сервера!")
        } catch (_: NullPointerException) {
        }
        logger.info("Работа сервера завершена")
        exitProcess(0)
    }

    /**
     * make a request on executing info
     *
     * @return information about collection
     */
    fun infoRequest(str: String): CommandResult {
        return if (str.split(Regex(" ")).toTypedArray().size == 1) {
            CommandResult(StateOfResponse.GOOD, "Информация о коллекции:\n" +
                    " Тип коллекции: " + collectionManager.getCollectionType() + "\n" +
                    " Размер коллекции: " + collectionManager.getCollectionSize() + "\n" +
                    " Дата и время последнего сохранения: " + collectionManager.getLastSaveTime() + "\n" +
                    " Дата и время последней иницилизации: " + collectionManager.getLastInitTime())
        } else CommandResult(StateOfResponse.ERROR,"error: После команды exit не должно быть аргументов")
    }

    /**
     * make a request on executing show
     *
     */
    fun showRequest(str: String): CommandResult {
        return if (str.split(Regex(" ")).toTypedArray().size == 1) {
            CommandResult(StateOfResponse.GOOD, collectionManager.toString())
        } else CommandResult(StateOfResponse.ERROR, "error: После команды show не должно быть аргументов")
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
        return if(listOfArguments.size == 16) {
            if (id == null) {
            val movie = Movie(
                collectionManager.generateId(),
                listOfArguments[0],
                Coordinates(listOfArguments[1].toFloat(), listOfArguments[2].toInt()),
                Date.from(Instant.now()).toString(),
                listOfArguments[3].toInt(),
                MovieGenre.valueOf(listOfArguments[4].uppercase(Locale.getDefault())),
                MpaaRating.valueOf(listOfArguments[5].uppercase(Locale.getDefault())),
                Person(listOfArguments[6], ZonedDateTime.of(listOfArguments[7].toInt(), listOfArguments[8].toInt(), listOfArguments[9].toInt(), 0, 0, 0, 0, ZoneId.systemDefault()).toString(), Color.valueOf(listOfArguments[10].uppercase(Locale.getDefault())), Location(listOfArguments[11].toFloat(), listOfArguments[12].toFloat(), listOfArguments[13].toFloat())),
                listOfArguments[14]
            )
            collectionManager.addObjectToCollection(movie)
            dataBaseHandler.addMovieToBD(movie)
            CommandResult(StateOfResponse.GOOD, "Кинотеатр создан и добавлен в коллекцию")
            } else {
                val movie = Movie(
                    id,
                    listOfArguments[0],
                    Coordinates(listOfArguments[1].toFloat(), listOfArguments[2].toInt()),
                    Date.from(Instant.now()).toString(),
                    listOfArguments[3].toInt(),
                    MovieGenre.valueOf(listOfArguments[4].uppercase(Locale.getDefault())),
                    MpaaRating.valueOf(listOfArguments[5].uppercase(Locale.getDefault())),
                    Person(listOfArguments[6], ZonedDateTime.of(listOfArguments[7].toInt(), listOfArguments[8].toInt(), listOfArguments[9].toInt(), 0, 0, 0, 0, ZoneId.systemDefault()).toString(), Color.valueOf(listOfArguments[10].uppercase(Locale.getDefault())), Location(listOfArguments[11].toFloat(), listOfArguments[12].toFloat(), listOfArguments[13].toFloat())),
                    listOfArguments[14]
                )
                collectionManager.addObjectToCollection(movie)
                dataBaseHandler.updateById(movie)
                CommandResult(StateOfResponse.GOOD, "Кинотеатр обновлен")
            }
        } else CommandResult(StateOfResponse.ERROR, "error: После команды add не должно быть аргументов")
    }

    /**
     * make a request on executing clear
     *
     * @return confirmation of the executing the command
     */
    fun clearRequest(str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        val collectionSize = collectionManager.getCollectionSize()
        return if (listOfArguments.size == 1) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(StateOfResponse.ERROR, "Коллекция пустая")
            } else {
                collectionManager.clearCollection(listOfArguments[0])
                dataBaseHandler.clearBD(listOfArguments[0])
                if (collectionSize == collectionManager.getCollectionSize()) {
                    CommandResult(StateOfResponse.GOOD, "Ваших кинотеатров не было в коллекции")
                } else CommandResult(StateOfResponse.GOOD, "Коллекция очищена от ваших кинотеатров")
            }
        } else CommandResult(StateOfResponse.ERROR, "error: После команды clear не должно быть аргументов")
    }

    /**
     * make a request on executing addIfMinOscarsCount
     *
     * @return confirmation of the executing the command
     */
    fun checkIfMinOscarsCountRequest(str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        return if(listOfArguments.size == 15) {
            if (collectionManager.compareOscarsWithMin(listOfArguments[3].toInt())) {
                addRequest(null, str)
            } else CommandResult(StateOfResponse.GOOD, "Есть элемент с меньшим количеством оскаров")
        } else CommandResult(StateOfResponse.ERROR, "error: После команды add_if_min не должно быть аргемнтов")
    }

    /**
     * make a request on executing save
     *
     */
    fun saveRequest(str: String): CommandResult {
        return if (str.split(Regex(" ")).toTypedArray().size == 1) {
            fileManager.writeCollection(collectionManager.getMoviesCollection())
            collectionManager.setLastSaveTime(LocalDateTime.now())
            CommandResult(StateOfResponse.GOOD)
        } else CommandResult(StateOfResponse.ERROR, "error: После команды save не должно быть аргументов")
    }

    /**
     * make a request on executing countAverageIfOscars
     *
     * @return confirmation of the executing the command
     */
    fun getAverageOscarsRequest(str: String): CommandResult {
        return if (str.split(Regex(" ")).toTypedArray().size == 1) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(StateOfResponse.GOOD, "Коллекция пустая")
            } else CommandResult(StateOfResponse.GOOD, "Среднее количество оскаров в кинотаетрах: " + collectionManager.getAverageOfOscars())
        } else CommandResult(StateOfResponse.ERROR, "error: После команды average_of_oscars_count не должно быть аргументов")
    }

    /**
     * make a request on executing CountGenreGreater
     *
     * @param str
     * @return confirmation of the executing the command
     */
    fun countGreaterGenreRequest(str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        return if (listOfArguments.size == 2) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(StateOfResponse.GOOD, "Коллекция пустая")
            } else {
                try {
                    val genre = MovieGenre.valueOf(listOfArguments[0].uppercase(Locale.getDefault()))
                    CommandResult(StateOfResponse.GOOD, "Количество жанров больше заданного: " + collectionManager.getCountOfGenreGreater(genre))
                } catch (e: IllegalArgumentException) {
                    return CommandResult(StateOfResponse.ERROR, "error: Такого жанра нету")
                }
            }
        } else CommandResult(StateOfResponse.ERROR, "error: Неправильный ввод данных")
    }

    /**
     * make a request on executing PrintOscarsCountInDescending
     *
     * @return confirmation of the executing the command
     */
    fun printOscarsCountRequest(str: String): CommandResult {
        return if (str.split(Regex(" ")).toTypedArray().size == 1) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(StateOfResponse.GOOD, "Коллекция пустая")
            } else CommandResult(StateOfResponse.GOOD, "Количество оскаров кинотеатров в порядке убывания: " + collectionManager.getOscarsCountsInDescending())
        } else CommandResult(StateOfResponse.ERROR, "error: После команды print_field_descending_oscars_count не должно быть аргументов")
    }

    /**
     * make a request on executing RemoveById
     *
     * @param str
     * @return confirmation of the executing the command
     */
    fun removeByIdRequest(str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        if (listOfArguments.size == 2) {
            try {
                if (collectionManager.getCollectionSize() == 0) {
                    return CommandResult(StateOfResponse.GOOD, "Коллекция пустая")
                } else {
                    val movie = collectionManager.getById(listOfArguments[0].toInt()) ?: return CommandResult(StateOfResponse.GOOD,"Нету кинотеатра с таким id")
                    return if (movie.getUsername() == listOfArguments[1]) {
                        collectionManager.removeFromCollection(movie)
                        dataBaseHandler.removeById(listOfArguments[0].toInt())
                        CommandResult(StateOfResponse.GOOD, "Кинотеатр удален")
                    } else CommandResult(StateOfResponse.ERROR, "error: Нельзя модифицировать чужие объекты!")
                }
            } catch (e: NumberFormatException) { return CommandResult(StateOfResponse.ERROR, "error: Неправильный ввод данных")
            }
        } else return CommandResult(StateOfResponse.ERROR, "error: После команды remove_by_id должно быть указано id")
    }

    /**
     * make a request on executing RemoveGreaterCountOfOscars
     *
     * @return confirmation of the executing the command
     */
    fun removeGreaterOscarsRequest(str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        return if (listOfArguments.size == 2) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(StateOfResponse.GOOD, "Коллекция пустая")
            } else {
                dataBaseHandler.removeWhereGreaterCountOscars(listOfArguments[0].toInt(), listOfArguments[1])
                CommandResult(StateOfResponse.GOOD, "Было удалено " + collectionManager.removeGreaterByOscars(listOfArguments[0].toInt(), listOfArguments[1]) + " кинотеатров, у которых больше оскаров")
            }
        } else CommandResult(StateOfResponse.ERROR, "error: После команды remove_greater не должно быть аргументов")
    }

    /**
     * make a request on executing RemoveLowerCountOfOscars
     *
     * @return confirmation of the executing the command
     */
    fun removeLowerOscarsRequest(str: String): CommandResult {
        val listOfArguments = str.split(Regex(" ")).toTypedArray()
        return if (listOfArguments.size == 2) {
            if (collectionManager.getCollectionSize() == 0) {
                CommandResult(StateOfResponse.GOOD, "Коллекция пустая")
            } else {
                dataBaseHandler.removeWhereLowerCountOscars(listOfArguments[0].toInt(), listOfArguments[1])
                CommandResult(StateOfResponse.GOOD, "Было удалено " + collectionManager.removeLowerByOscars(listOfArguments[0].toInt(), listOfArguments[1]) + " кинотеатров, у которых меньше оскаров")
            }
        } else CommandResult(StateOfResponse.ERROR, "error: После команды remove_lower не должно быть аргументов")
    }

    /**
     * make a request on executing UpdateById
     *
     * @param str
     * @return confirmation of the executing the command
     */
    fun updateByIdRequest(str: String): CommandResult {
        if (str.split(Regex(" ")).toTypedArray().size == 17) {
            try {
                if (collectionManager.getCollectionSize() == 0) {
                    return CommandResult(StateOfResponse.GOOD, "Коллекция пустая")
                } else {
                    var listOfArguments = str.split(Regex(" ")).toTypedArray()
                    val movie = collectionManager.getById(listOfArguments[0].toInt()) ?: return CommandResult(StateOfResponse.GOOD, "Нету кинотеатра с таким id")
                    val id = listOfArguments[0].toInt()
                    return if (movie.getUsername() == listOfArguments[16]) {
                        collectionManager.removeFromCollection(movie)

                        val list = listOfArguments.toMutableList()
                        list.removeAt(0)
                        listOfArguments = list.toTypedArray()

                        addRequest(id, listOfArguments.joinToString(separator = " "))
                    } else CommandResult(StateOfResponse.ERROR, "error: Нельзя модифицировать чужие объекты!")
                }
            }catch (e: NumberFormatException) { return CommandResult(StateOfResponse.ERROR, "error: Неправильный ввод данных") }
        } else return CommandResult(StateOfResponse.ERROR, "error: После команды update должно быть указано id")
    }
}