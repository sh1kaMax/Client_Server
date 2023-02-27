package commands

import collection.Movie
import exceptions.EmptyCollectionException
import exceptions.NoMovieByIdException
import utility.Asker
import utility.CollectionManager

class UpdateById(collectionManager: CollectionManager, asker: Asker): AbsctractCommand("update id {element}", "обновить значение элемента коллекции, id которого равен заданному"){
    private var collectionManager: CollectionManager
    private var asker: Asker

    init {
        this.collectionManager = collectionManager
        this.asker = asker
    }

    override fun execute(str: String): Boolean {
        if (!str.isEmpty()) {
            try {
                if (collectionManager.getCollectionSize() == 0) throw EmptyCollectionException()
                val id: Int = str.toInt()
                val movie = collectionManager.getById(id) ?: throw NoMovieByIdException()
                if (!asker.getScriptMode()) println("Используется команда " + getName())

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
                if(asker.askQuestion("Хотите изменить женр?")) genre = asker.askForGenre()
                if(asker.askQuestion("Хотите изменить рейтинг?")) rating = asker.askForRating()
                if(asker.askQuestion("Хотите изменить директора?")) director = asker.askForPerson()

                collectionManager.addObjectToCollection(Movie(
                    id,
                    movieName,
                    coordinates,
                    creationDate,
                    oscars,
                    genre,
                    rating,
                    director
                ))
                println("Кинотеатр изменен")
            }catch (e: EmptyCollectionException) {
                println("error: Коллекция пуста!")
            }catch (e: NoMovieByIdException){
                println("error: Нету кинотеатра с таким id!")
            }catch (e: NumberFormatException){
                println("error: id должен быть числом")
            }
            return true
        } else {
            println("error: Неправильно введена команда!")
            return false
        }
    }
}