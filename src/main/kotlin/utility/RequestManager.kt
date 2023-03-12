package utility

import collection.*
import commandExecuter
import java.time.Instant
import java.util.*
import kotlin.system.exitProcess

class RequestManager(private var collectionManager: CollectionManager,
                     private var asker: Asker,
                     ) {

    fun helpRequest() {
        commandExecuter.help()
    }

    fun exitRequest() {
        exitProcess(0)
    }

    fun infoRequest() {
        var lastInitTime = collectionManager.getLastInitTime().toString()
        if (lastInitTime == "null") lastInitTime = "иницилизация еще не произовидилась"
        var lastSaveTime = collectionManager.getLastSaveTime().toString()
        if (lastSaveTime == "null") lastSaveTime = "сохранение еще не производилось"
        println(
            "Информация о коллекции:\n" +
                    " Тип коллекции: " + collectionManager.getCollectionType() + "\n" +
                    " Размер коллекции: " + collectionManager.getCollectionSize() + "\n" +
                    " Дата и время последнего сохранения: " + lastSaveTime + "\n" +
                    " Дата и время последней иницилизации: " + lastInitTime)
    }

    fun showRequest() {
        println(collectionManager)
    }

    fun addRequest(id: Int?, movieName: String?, coordinates: Coordinates?,creationDate: Date?, oscars: Int?, genre: MovieGenre?, rating: MpaaRating?, person: Person?): String {
        collectionManager.addObjectToCollection(
            Movie(
            id ?: collectionManager.generateId(),
            movieName ?: asker.askForMovieName(),
            coordinates ?: asker.askForCoordinates(),
            creationDate ?: Date.from(Instant.now()),
            oscars ?: asker.askForOscarsCount(),
            genre ?: asker.askForGenre(),
            rating ?: asker.askForRating(),
            person ?: asker.askForPerson()
        ))
        return "Кинотеатр создан и добавлен в коллекцию"
    }

    fun clearRequest(): String {
        return if (collectionManager.getCollectionSize() == 0) {
            "Коллекция пустая"
        } else {
            collectionManager.clearCollection()
            "Коллекция очищена"
        }
    }

    fun checkIfMinOscarsCountRequest(): String {
        val oscars = asker.askForOscarsCount()
        return if (collectionManager.compareOscarsWithMin(oscars)) {
            addRequest(null, null, null, null, oscars, null, null, null)
        } else "Есть элемент с меньшим количеством оскаров"
    }

    fun saveRequest() {
        collectionManager.saveCollection()
    }

    fun getAverageOscarsRequest(): String {
        return if (collectionManager.getCollectionSize() == 0) {
            "Коллекция пустая"
        } else "Среднее количество оскаров в кинотаетрах: " + collectionManager.getAverageOfOscars()
    }

    fun countGreaterGenreRequest(str: String): String {
        return if (collectionManager.getCollectionSize() == 0) {
            "Коллекция пустая"
        } else {
            val genre = MovieGenre.valueOf(str.uppercase(Locale.getDefault()))
            "Количество жанров больше заданного: " + collectionManager.getCountOfGenreGreater(genre)
        }
    }

    fun printOscarsCountRequest(): String {
        return if (collectionManager.getCollectionSize() == 0) {
            "Коллекция пустая"
        } else "Количество оскаров кинотеатров в порядке убывания: " + collectionManager.getOscarsCountsInDescending()
    }

    fun removeByIdRequest(str: String): String {
        if (collectionManager.getCollectionSize() == 0) {
            return "Коллекция пустая"
        } else {
            val movie = collectionManager.getById(str.toInt()) ?: return "Нету кинотеатра с таким id"
            collectionManager.removeFromCollection(movie)
            return "Кинотеатр удален"
        }
    }

    fun removeGreaterOscarsRequest(): String {
        return if (collectionManager.getCollectionSize() == 0) {
            "Коллекция пустая"
        } else {
            "Было удалено " + collectionManager.removeGreaterByOscars(asker.askForOscarsToRemoveGreater()) + " кинотеатров, у которых больше оскаров"
        }
    }

    fun removeLowerOscarsRequest(): String {
        return if (collectionManager.getCollectionSize() == 0) {
            "Коллекция пустая"
        } else {
            "Было удалено " + collectionManager.removeLowerByOscars(asker.askForOscarsToRemoveLower()) + " кинотеатров, у которых меньше оскаров"
        }
    }

    fun updateByIdRequest(str: String): String {
        if (collectionManager.getCollectionSize() == 0) {
            return "Коллекция пустая"
        } else {
            val movie = collectionManager.getById(str.toInt()) ?: return "Нету кинотеатра с таким id"

            var movieName = movie.getName()
            var coordinates = movie.getCoordinates()
            val creationDate = movie.getCreationDate()
            var oscars = movie.getOscarsCount()
            var genre = movie.getGenre()
            var rating = movie.getMpaaRating()
            var director = movie.getDirector()
            collectionManager.removeFromCollection(movie)

            if(asker.askQuestion("Хотите изменить название кинотеатра?")) movieName = asker.askForMovieName()
            if(asker.askQuestion("Хотите изменить координаты кинотеатра?")) coordinates = asker.askForCoordinates()
            if(asker.askQuestion("Хотите изменить количество оскаров?")) oscars = asker.askForOscarsCount()
            if(asker.askQuestion("Хотите изменить жанр?")) genre = asker.askForGenre()
            if(asker.askQuestion("Хотите изменить рейтинг?")) rating = asker.askForRating()
            if(asker.askQuestion("Хотите изменить директора?")) director = asker.askForPerson()

            return addRequest(str.toInt(), movieName, coordinates, creationDate, oscars, genre, rating, director)
        }
    }
}