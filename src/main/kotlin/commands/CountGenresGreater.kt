package commands

import asker
import collection.MovieGenre
import exceptions.EmptyCollectionException
import utility.CollectionManager
import java.util.*

class CountGenresGreater(collectionManager: CollectionManager): AbsctractCommand("count_greater_than_genre genre", "вывести количество элементов, значения поля genre которых больше заданного") {
    private var collectionManager: CollectionManager

    init {
        this.collectionManager = collectionManager
    }

    override fun execute(str: String): Boolean {
        if (!str.isEmpty()){
            try {
                if (collectionManager.getCollectionSize() == 0) throw EmptyCollectionException()
                val genre = MovieGenre.valueOf(str.uppercase(Locale.getDefault()))
                if (!asker.getScriptMode()) println("Используется команда " + getName())
                println("Количество жанров больше заданного: " + collectionManager.getCountOfGenreGreater(genre))
            }catch (e: EmptyCollectionException){
                println("error: Коллекция пустая!")
            }catch (e: IllegalArgumentException){
                println("error: Такого жанра нету!")
            }
            return true
        }else{
            println("error: Неправильно введена команда!")
            return false
        }
    }
}