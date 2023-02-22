package commands

import exceptions.EmptyCollectionException
import utility.CollectionManager
import java.lang.NumberFormatException

class Clear(collectionManager: CollectionManager): AbsctractCommand("clear", "очистить коллекцию") {
    private var collectionManager: CollectionManager

    init {
        this.collectionManager = collectionManager
    }

    override fun execute(str: String): Boolean {
        if (str.isEmpty()) {
            try {
                if (collectionManager.getCollectionSize() == 0) throw EmptyCollectionException()
                println("Используется команда " + getName())
                collectionManager.clearCollection()
                println("Коллекция очищена")
            } catch (e: EmptyCollectionException) {
                println("error: Коллекция пустая!")
            } catch (e: NumberFormatException) {
                println("error: id должен быть числом!")
            }
            return true
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}