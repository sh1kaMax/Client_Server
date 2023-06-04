package utility

import collection.Movie
import exceptions.IsEmptyException
import exceptions.NotInTrueFormatException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import logger
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.*

/**
 * File manager
 *
 * @property name
 * @property fabrique
 * @constructor Create File manager
 */
class FileManager {

    private var name = ""

    /**
     * Write collection on the file
     *
     * @param collection
     */
    fun writeCollection(collection: TreeSet<Movie>) {
        try {
            val list = ArrayList(collection)
            FileWriter(name).use { it.write(Json.encodeToString(list)) }
            logger.info("Коллекция успешно записана на файл")
        } catch (e: IOException) {
            logger.error("error: Невозможно сохранить!")
        }
    }

    /**
     * Read collection from the file
     *
     * @return collection
     */
    fun readCollection(): TreeSet<Movie> {
        var fileReader: FileReader
        while (true) {
            try {
                fileReader = FileReader(name)
                break
            } catch (e: FileNotFoundException) {
                logger.error("error: Файла с таким названием нету!")
            }
        }
        var char: Int
        var input = ""
        do {
            char = fileReader.read()
            if (char == -1) {
                break
            }
            input += char.toChar()
        } while (true)
        return TreeSet(Json.decodeFromString<List<Movie>>(input))
    }

    /**
     * Set name
     *
     * @param name
     */
    private fun setName(name: String) {
        this.name = name
    }

    private fun askForFileName(): String {
        var fileName: String
        val scan = Scanner(System.`in`)
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
}