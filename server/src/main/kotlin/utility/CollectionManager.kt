package utility

import collection.Movie
import collection.MovieGenre
import dataBaseHandler
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock


/**
 * Collection manager
 *
 * @property moviesCollection
 * @constructor Create Collection manager
 */
class CollectionManager {

    private var moviesCollection  = TreeSet<Movie>()
    private var lastInitTime: LocalDateTime? = null
    private var lastSaveTime: LocalDateTime? = null
    private var collectionLocker = ReentrantReadWriteLock()


init {
    dataBaseHandler.connectToDataBase()
    moviesCollection = dataBaseHandler.loadCollectionFromBD()
}

    fun getMoviesCollection(): TreeSet<Movie>{
        collectionLocker.readLock().lock()
        try {
            return moviesCollection
        } finally {
            collectionLocker.readLock().unlock()
        }
    }

    /**
     * Get last init time
     *
     * @return date of last initialization
     */
    fun getLastInitTime(): String {
        return if (lastInitTime == null) {
            "иницилизация еще не произовидилась"
        } else lastInitTime.toString()
    }

    /**
     * Get last save time
     *
     * @return date of last save
     */
    fun getLastSaveTime(): String {
        return if (lastSaveTime == null) {
            "сохранение еще не производилось"
        } else lastSaveTime.toString()
    }

    /**
     * Get collection type
     *
     * @return type of the collection
     */
    fun getCollectionType(): String {
//        return moviesCollection.javaClass.name.substring(10..16)
        return moviesCollection.javaClass.typeName
    }

    /**
     * Get collection size
     *
     * @return size of the collection
     */
    fun getCollectionSize(): Int {
        return moviesCollection.size
    }

    /**
     * Get by id
     *
     * @param id
     * @return movie by id or null if no movie with this id
     */
    fun getById(id: Int): Movie? {
        collectionLocker.readLock().lock()
        try {
            return moviesCollection.find { movie -> movie.getId() == id }
        } finally {
            collectionLocker.readLock().unlock()
        }
    }

    /**
     * Generate id
     *
     * @return a new id
     */
    fun generateId(): Int {
        if (moviesCollection.isEmpty()) return 1
        return moviesCollection.last().getId() + 1
    }

    fun setLastSaveTime(data: LocalDateTime) {
        this.lastSaveTime = data
    }

    /**
     * Add movie to the collection
     *
     * @param movie
     */
    fun addObjectToCollection(movie: Movie) {
        collectionLocker.writeLock().lock()
        try {
            moviesCollection.add(movie)
        } finally {
            collectionLocker.writeLock().unlock()
        }
    }

    /**
     * Remove a movie from the collection
     *
     * @param movie
     */
    fun removeFromCollection(movie: Movie) {
        collectionLocker.writeLock().lock()
        try {
            moviesCollection.remove(movie)
        } finally {
            collectionLocker.writeLock().unlock()
        }
    }

    /**
     * Clear the collection
     *
     */
    fun clearCollection(username: String) {
        collectionLocker.writeLock().lock()
        try {
            moviesCollection.removeIf { movie -> movie.getUsername() == username}
        } finally {
            collectionLocker.writeLock().unlock()
        }
    }

    /**
     * Remove movies which greater by oscars
     *
     * @param oscars
     * @return count of deleted movies
     */
    fun removeGreaterByOscars(oscars: Int, username: String): Int {
        collectionLocker.writeLock().lock()
        try {
            val oldSize = moviesCollection.size
            moviesCollection.removeIf { movie -> movie.getOscarsCount() > oscars && movie.getUsername() == username}
            return oldSize - moviesCollection.size
        } finally {
            collectionLocker.writeLock().unlock()
        }
    }

    /**
     * Remove movies which lower by oscars
     *
     * @param oscars
     * @return count of deleted movies
     */
    fun removeLowerByOscars(oscars: Int, username: String): Int {
        collectionLocker.writeLock().lock()
        try {
            val oldSize = moviesCollection.size
            moviesCollection.removeIf { movie -> movie.getOscarsCount() < oscars && movie.getUsername() == username}
            return oldSize - moviesCollection.size
        } finally {
            collectionLocker.writeLock().unlock()
        }
    }

    /**
     * Compare given oscars count with min oscars count of the collection
     *
     * @param int
     * @return True if given count lower. False if greater
     */
    fun compareOscarsWithMin(int: Int): Boolean {
        collectionLocker.readLock().lock()
        try {
            return int < moviesCollection.minOf { movie -> movie.getOscarsCount() }
        } finally {
            collectionLocker.readLock().unlock()
        }
    }

    /**
     * Get average oscars count of movies from the collection
     *
     * @return average oscars count
     */
    fun getAverageOfOscars(): Double {
        collectionLocker.readLock().lock()
        try {
            return (moviesCollection.sumOf { movie -> movie.getOscarsCount() } / moviesCollection.size.toDouble())
        } finally {
            collectionLocker.writeLock().unlock()
        }
    }

    /**
     * Get genres count which greater than given genre
     *
     * @param genreIn
     * @return count of genres
     */
    fun getCountOfGenreGreater(genreIn: MovieGenre): Int {
        collectionLocker.readLock().lock()
        try {
            return moviesCollection.count { movie -> movie.getGenre().toString().length > genreIn.toString().length }
        } finally {
            collectionLocker.readLock().unlock()
        }
    }

    /**
     * Get oscars counts in descending
     *
     * @return oscars count in descending
     */
    fun getOscarsCountsInDescending(): String {
        collectionLocker.readLock().lock()
        try {
            val list = ArrayList<String>()
            ((moviesCollection.sortedByDescending { movie -> movie.getOscarsCount() }).forEach { movie -> list.add(movie.getOscarsCount().toString()) })
            return list.joinToString()
        } finally {
            collectionLocker.readLock().unlock()
        }
    }


    override fun toString(): String {
        if (moviesCollection.isEmpty()) return "Коллекция пустая"

        var collection = ""
        for(movie in moviesCollection) {
            collection += movie
            if(movie != moviesCollection.last()) collection += "\n"
        }
        return collection
    }
}