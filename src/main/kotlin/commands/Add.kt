package commands

import collection.*
import utility.CollectionManager
import utility.Asker
import java.time.Instant
import java.util.*

class Add(collectionManager: CollectionManager, asker: Asker): AbsctractCommand("add {element}", "добавить новый элемент в коллекцию") {
    private var collectionManager: CollectionManager
    private var asker: Asker

    init {
        this.collectionManager = collectionManager
        this.asker = asker
    }

    override fun execute(str: String): Boolean {
        if (str.isEmpty()) {
            println("Используется команда " + getName())
            collectionManager.addObjectToCollection(Movie(
                collectionManager.generateId(),
                asker.askForMovieName(),
                asker.askForCoordinates(),
                Date.from(Instant.now()),
                asker.askForOscarsCount(),
                asker.askForGenre(),
                asker.askForRating(),
                asker.askForPerson()
            ))
            println("Кинотеатр создан и добавлен в коллекцию")
            return true
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}