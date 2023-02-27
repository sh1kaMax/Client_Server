package commands

import asker
import utility.CollectionManager

class Info(collectionManager: CollectionManager): AbsctractCommand("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.") {
    private var collectionManager: CollectionManager
    init {
        this.collectionManager = collectionManager
    }

    override fun execute(str: String): Boolean {
        if (str.isEmpty()) {
            if (!asker.getScriptMode()) println("Используется команда " + getName())
            var lastInitTime: String = collectionManager.getLastInitTime().toString()
            if (lastInitTime == "null") lastInitTime = "иницилизация еще не произовидилась"
            var lastSaveTime: String = collectionManager.getLastSaveTime().toString()
            if (lastSaveTime == "null") lastSaveTime = "сохранение еще не производилось"
            println(
                "Информация о коллекции:\n" +
                        " Тип коллекции: " + collectionManager.getCollectionType() + "\n" +
                        " Размер коллекции: " + collectionManager.getCollectionSize() + "\n" +
                        " Дата и время последнего сохранения: " + lastSaveTime + "\n" +
                        " Дата и время последней иницилизации: " + lastInitTime
            )
            return true
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}