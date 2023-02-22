package commands

import exceptions.EmptyCollectionException
import utility.Asker
import utility.CollectionManager

class RemoveLowerCountOfOscars(collectionManager: CollectionManager, asker: Asker): AbsctractCommand("remove_greater", "удалить из коллекции все элементы, превышающие заданное количество оскаров") {
    private var collectionManager: CollectionManager
    private var asker: Asker

    init {
        this.collectionManager = collectionManager
        this.asker = asker
    }

    override fun execute(str: String): Boolean {
        if (str.isEmpty()) {
            try {
                if (collectionManager.getCollectionSize() == 0) throw EmptyCollectionException()
                println("Используется команда " + getName())
                var oscars = asker.askForOscarsToRemoveLower()
                oscars = collectionManager.removeLowerByOscars(oscars)
                if (oscars == 0) {
                    println("Таких кинотеатров не было")
                }else {
                    println("Было удалено $oscars кинотеатров, превышающих данное количество")
                }
            } catch (e: EmptyCollectionException) {
                println("error: Коллекция пустая!")
            }
            return true
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}