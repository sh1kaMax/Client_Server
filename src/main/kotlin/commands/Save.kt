package commands

import utility.CollectionManager

class Save(collectionManager: CollectionManager): AbsctractCommand("save", "сохранить коллекцию в файл") {
    private var collectionManager: CollectionManager

    init {
        this.collectionManager = collectionManager
    }

    override fun execute(str: String): Boolean {
        if (str.isEmpty()){
            collectionManager.saveCollection()
            return true
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}