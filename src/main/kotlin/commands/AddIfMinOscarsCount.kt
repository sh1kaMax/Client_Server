package commands

import collection.Movie
import utility.Asker
import utility.CollectionManager
import java.time.Instant
import java.util.*

class AddIfMinOscarsCount(collectionManager: CollectionManager, asker: Asker): AbsctractCommand("add_if_min {element}", "добавить новый элемент в коллекцию, если количество оскаров меньше, чем у элемента с наименьшим количеством оскаров") {
    private var collectionManager: CollectionManager
    private var asker: Asker

    init {
        this.collectionManager = collectionManager
        this.asker = asker
    }

    override fun execute(str: String): Boolean {
        if (str.isEmpty()) {
            if (collectionManager.getMinCountOfOscars() != 1) {
                println("Используется команда " + getName())
                val oscars = asker.askForOscarsCount()
                if (collectionManager.compareOscarsWithMin(oscars)) {
                    collectionManager.addObjectToCollection(
                        Movie(
                            collectionManager.generateId(),
                            asker.askForMovieName(),
                            asker.askForCoordinates(),
                            Date.from(Instant.now()),
                            oscars,
                            asker.askForGenre(),
                            asker.askForRating(),
                            asker.askForPerson()
                        ))
                    println("Кинотеатр создан и добавлен в коллекцию")
                    return true
                } else {
                    println("Есть элимент с меньшим количеством оскаров")
                    return false
                }
            }else {
                println("Уже есть элемент с минимальным количеством оскаров")
                return false
            }
        }else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}