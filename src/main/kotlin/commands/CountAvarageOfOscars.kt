package commands

import exceptions.EmptyCollectionException
import utility.CollectionManager

class CountAvarageOfOscars(collectionManager: CollectionManager): AbsctractCommand("average_of_oscars_count", "вывести среднее значение поля OscarsCount для всех элементов коллекции") {
    private var collectionManager: CollectionManager

    init {
        this.collectionManager = collectionManager
    }

    override fun execute(str: String): Boolean {
        if (str.isEmpty()) {
            try {
                if (collectionManager.getCollectionSize() == 0) throw EmptyCollectionException()
                println("Используется команда " + getName())
                println("Среднее количество оскаров в кинотаетрах: " + collectionManager.getAverageOfOscars())
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