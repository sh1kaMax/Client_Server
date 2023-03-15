package utility

import collection.Movie
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
class FileManager(private var name: String,
                  private var fabrique: Fabrique
                  ) {


    /**
     * Write collection on the file
     *
     * @param collection
     */
    fun writeCollection(collection: TreeSet<Movie>) {
        try {
            val list = ArrayList(collection)
            FileWriter(name).use { it.write(Json.encodeToString(list)) }
        } catch (e: IOException) {
            println("error: Невозможно сохранить!")
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
                println("error: Файла с таким названием нету!")
                setName(fabrique.askForFileName())
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
}