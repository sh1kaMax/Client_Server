package commands

import asker
import utility.CollectionManager

class Show(collectionManager: CollectionManager): AbsctractCommand("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"){
    private var collectionManager: CollectionManager

    init {
        this.collectionManager = collectionManager
    }

    override fun execute(str: String): Boolean {
        if(str.isEmpty()) {
            if (!asker.getScriptMode()) println("Используется команда " + getName())
            println(collectionManager)
            return true
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}