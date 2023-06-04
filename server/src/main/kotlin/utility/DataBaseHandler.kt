package utility

import collection.*
import hasher
import java.io.FileNotFoundException
import java.io.FileReader
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import kotlin.system.exitProcess

class DataBaseHandler {
    private lateinit var connection: Connection
    private val pepper = "2jd*1ld?wp@"
    private val requestFindUsername = "SELECT COUNT(*) AS times FROM USERS WHERE username = ?"
    private val requestAddUsername = "INSERT INTO USERS (username, password, salt) VALUES (?, ?, ?)"
    private val requestValidateUser = "SELECT COUNT(*) AS times FROM USERS WHERE username = ? and password = ?"
    private val requestFindSalt = "SELECT salt FROM USERS WHERE username = ?"
    private val requestSelectCollection = "SELECT * FROM MOVIE"
    private val requestAddMovie = "INSERT INTO MOVIE (movie_id, movie_name, coordinates_x, coordinates_y, creation_date, oscars_count, movie_genre, mpaa_rating, person_name, person_birthday, person_eye_color, person_location_x, person_location_y, person_location_z, username_creation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    private val requestClear = "DELETE FROM MOVIE WHERE username_creation = ?"
    private val requestRemoveById = "DELETE FROM MOVIE WHERE movie_id = ?"
    private val requestRemoveGreaterThanOscars = "DELETE FROM MOVIE WHERE oscars_count > ? AND username_creation = ?"
    private val requestRemoveLowerThanOscars = "DELETE FROM MOVIE WHERE oscars_count < ? AND username_creation = ?"
    private val requestUpdateById = "UPDATE MOVIE SET movie_name = ?, coordinates_x = ?, coordinates_y = ?, creation_date = ?, oscars_count = ?, movie_genre = ?, mpaa_rating = ?, person_name = ?, person_birthday = ?, person_eye_color = ?, person_location_x = ?, person_location_y = ?, person_location_z = ?, username_creation = ? WHERE movie_id = ?"
    private val username: String
    private val password: String
    private val url = "jdbc:postgresql://localhost:4806/studs"

    init {
        val loginScanner: Scanner

        try {
            Class.forName("org.postgresql.Driver")
        } catch (e: ClassNotFoundException) {
            println("PostgreSQL драйвер не найден")
            exitProcess(-1)
        }

        try { loginScanner = Scanner(FileReader("login.txt"))
        } catch (e: FileNotFoundException) {
            println("Потерялся файл с данными для входа на БД")
            exitProcess(-1)
        }

        try {
            username = loginScanner.nextLine().trim()
            password = loginScanner.nextLine().trim()
        } catch (e: NoSuchElementException) {
            println("Данные для входа некорректно записаны в файл")
            exitProcess(-1)
        }
    }
    fun connectToDataBase() {
        try {
            connection = DriverManager.getConnection(url, username, password)
            println("Подключение к БД успешно завершено")
        } catch (e: SQLException) {
            println("Не удалось подключиться к БД")
            exitProcess(-1)
        }
    }

    fun loadCollectionFromBD(): TreeSet<Movie> {
        val collection = TreeSet<Movie>()
        try {
            val selectCollection = connection.prepareStatement(requestSelectCollection)
            val result = selectCollection.executeQuery()
            while (result.next()) {
                collection.add(selectMovieFromBD(result))
            }
            selectCollection.close()
            println("Коллекция успешно загружена. Её размер: " + collection.size)
        } catch (e: SQLException) {
            println("error: Ошибка при загрузке коллекци из БД")
            e.printStackTrace()
            exitProcess(-1)
        }
        return collection
    }

    private fun selectMovieFromBD(result: ResultSet): Movie {
        return Movie(result.getInt("movie_id"),
                    result.getString("movie_name"),
                    Coordinates(result.getFloat("coordinates_x"),
                                result.getInt("coordinates_y")),
                    result.getString("creation_date"),
                    result.getInt("oscars_count"),
                    MovieGenre.valueOf(result.getString("movie_genre")),
                    MpaaRating.valueOf(result.getString("mpaa_rating")),
                    Person(result.getString("person_name"),
                            result.getString("person_birthday"),
                            Color.valueOf(result.getString("person_eye_color")),
                            Location(result.getFloat("person_location_x"),
                                     result.getFloat("person_location_y"),
                                     result.getFloat("person_location_z"))),
                    result.getString("username_creation")
            )
    }

    fun registerUserToBD(username: String, password: String): Boolean {
        if (usernameExist(username)) return false
        val addUser = connection.prepareStatement(requestAddUsername)
        val salt = getRandomString()
        addUser.setString(1, username)
        addUser.setString(2, hasher.encryptStringToSHA256(salt + password + pepper))
        addUser.setString(3, salt)
        addUser.executeUpdate()
        addUser.close()
        return true
    }

    private fun usernameExist(username: String): Boolean {
        val findUsername = connection.prepareStatement(requestFindUsername)
        findUsername.setString(1, username)
        val result = findUsername.executeQuery()
        result.next()
        return if (result.getInt(1) == 1) {
            findUsername.close()
            true
        } else {
            findUsername.close()
            false
        }
    }

    fun validateUser(username: String, password: String): Boolean {
        val findSalt = connection.prepareStatement(requestFindSalt)
        findSalt.setString(1, username)
        val test = findSalt.executeQuery()
        test.next()
        val salt = test.getString(1)
        val validateUser = connection.prepareStatement(requestValidateUser)
        validateUser.setString(1, username)
        validateUser.setString(2, hasher.encryptStringToSHA256(salt + password + pepper))
        val result = validateUser.executeQuery()
        result.next()
        return if (result.getInt(1) == 1) {
            validateUser.close()
            true
        } else {
            validateUser.close()
            false
        }
    }

    fun addMovieToBD(movie: Movie) {
        connection.autoCommit = false
        connection.setSavepoint()
        val addMovie = connection.prepareStatement(requestAddMovie)
        addMovie.setInt(1, movie.getId())
        addMovie.setString(2, movie.getName())
        addMovie.setFloat(3, movie.getCoordinates().getX())
        addMovie.setInt(4, movie.getCoordinates().getY())
        addMovie.setString(5, movie.getCreationDate())
        addMovie.setInt(6, movie.getOscarsCount())
        addMovie.setString(7, movie.getGenre().toString())
        addMovie.setString(8, movie.getMpaaRating().toString())
        addMovie.setString(9, movie.getDirector().getName())
        addMovie.setString(10, movie.getDirector().getBirthday())
        addMovie.setString(11, movie.getDirector().getColor().toString())
        addMovie.setFloat(12, movie.getDirector().getLocation().getX())
        addMovie.setFloat(13, movie.getDirector().getLocation().getY())
        addMovie.setFloat(14, movie.getDirector().getLocation().getZ())
        addMovie.setString(15, movie.getUsername())
        addMovie.executeUpdate()
        addMovie.close()
        connection.commit()
        connection.autoCommit = true
    }

    fun clearBD(username: String) {
        val clearBD = connection.prepareStatement(requestClear)
        clearBD.setString(1, username)
        clearBD.executeUpdate()
        clearBD.close()
    }

    fun removeById(id: Int) {
        val removeById = connection.prepareStatement(requestRemoveById)
        removeById.setInt(1, id)
        removeById.executeUpdate()
        removeById.close()
    }

    fun removeWhereGreaterCountOscars(count: Int, username: String) {
        val removeGreaterOscars = connection.prepareStatement(requestRemoveGreaterThanOscars)
        removeGreaterOscars.setInt(1, count)
        removeGreaterOscars.setString(2, username)
        removeGreaterOscars.executeUpdate()
        removeGreaterOscars.close()
    }

    fun removeWhereLowerCountOscars(count: Int, username: String) {
        val removeLowerOscars = connection.prepareStatement(requestRemoveLowerThanOscars)
        removeLowerOscars.setInt(1, count)
        removeLowerOscars.setString(2, username)
        removeLowerOscars.executeUpdate()
        removeLowerOscars.close()
    }

    fun updateById(movie: Movie) {
        val updateById = connection.prepareStatement(requestUpdateById)
        updateById.setString(1, movie.getName())
        updateById.setFloat(2, movie.getCoordinates().getX())
        updateById.setInt(3, movie.getCoordinates().getY())
        updateById.setString(4, movie.getCreationDate())
        updateById.setInt(5, movie.getOscarsCount())
        updateById.setString(6, movie.getGenre().toString())
        updateById.setString(7, movie.getMpaaRating().toString())
        updateById.setString(8, movie.getDirector().getName())
        updateById.setString(9, movie.getDirector().getBirthday())
        updateById.setString(10, movie.getDirector().getColor().toString())
        updateById.setFloat(11, movie.getDirector().getLocation().getX())
        updateById.setFloat(12, movie.getDirector().getLocation().getY())
        updateById.setFloat(13, movie.getDirector().getLocation().getZ())
        updateById.setString(14, movie.getUsername())
        updateById.setInt(15, movie.getId())
        updateById.executeUpdate()
        updateById.close()
    }

    fun getRandomString(): String{
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..10)
            .map { charset.random() }
            .joinToString("")
    }
}