package commands

import asker
import exceptions.EmptyCollectionException
import exceptions.NoMovieByIdException
import utility.CollectionManager

class RemoveById(collectionManager: CollectionManager): AbsctractCommand("remove_by_id id", "удалить элемент из коллекции по его id") {
    private var collectionManager: CollectionManager

    init {
        this.collectionManager = collectionManager
    }

    override fun execute(str: String): Boolean {
        if (!str.isEmpty()) {
            try {
                if (collectionManager.getCollectionSize() == 0) throw EmptyCollectionException()
                val id = str.toInt()
                if (!asker.getScriptMode()) println("Используется команда " + getName())
                val movie = collectionManager.getById(id) ?: throw NoMovieByIdException()
                collectionManager.removeFromCollection(movie)
                println("Кинотеатр удален")
            } catch (e: NumberFormatException) {
                println("error: id должно быть числом!")
            } catch (e: EmptyCollectionException) {
                println("error: Коллекция пустая!")
            } catch (e: NoMovieByIdException) {
                println("error: Нету кинотеатра с таким id!")
            }
            return true
        }else {
            println("error: Неправильно введена команда")
            return false
        }
    }
}