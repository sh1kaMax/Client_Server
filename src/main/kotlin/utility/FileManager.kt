package utility
import asker
import collection.Movie
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.*

class FileManager(private var name: String) {
    private val json: Json = Json

    fun writeCollection(collection: TreeSet<Movie>) {
        try {
            val list = ArrayList(collection)
            FileWriter(name).use { it.write(json.encodeToString(list)) }
            println("Коллекция сохранена на файл")
        } catch (e: IOException) {
            println("error: Невозможно сохранить!")
        }
    }

    fun readCollection(): TreeSet<Movie> {
        var fileReader: FileReader
        while (true) {
            try {
                fileReader = FileReader(name)
                break
            } catch (e: FileNotFoundException) {
                println("error: Файла с таким названием нету!")
                setName(asker.askForFileName())
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
        return TreeSet(json.decodeFromString<List<Movie>>(input))
    }

    fun setName(name: String) {
        this.name = name
    }
}