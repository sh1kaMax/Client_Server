package commands

import asker
import exceptions.EmptyCollectionException
import utility.CollectionManager

class PrintOscarsCountsInDescending(collectionManager: CollectionManager): AbsctractCommand("print_field_descending_oscars_count", "вывести значения поля oscarsCount всех элементов в порядке убывания") {
    private var collectionManager: CollectionManager

    init {
        this.collectionManager = collectionManager
    }

    override fun execute(str: String): Boolean {
        if (str.isEmpty()) {
            try {
                if (collectionManager.getCollectionSize() == 0) throw EmptyCollectionException()
                if (!asker.getScriptMode()) println("Используется команда " + getName())
                println("Количество оскаров кинотеатров в порядке убывания: " + collectionManager.getOscarsCountsInDescending())
            }catch (e: EmptyCollectionException) {
                println("error: Коллекция пустая!")
            }
            return true
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}