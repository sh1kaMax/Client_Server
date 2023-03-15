package commands

import org.junit.jupiter.api.Test
import utility.CollectionManager
import utility.Fabrique
import utility.FileManager
import utility.RequestManager
import java.util.*
import kotlin.test.assertEquals

class InfoRequestTest {

    private val fabrique = Fabrique(Scanner(System.`in`))
    private var fileManager = FileManager("lab5.json", fabrique)
    private val collectionManager = CollectionManager(fileManager.readCollection())
    private val requestManager = RequestManager(collectionManager, fabrique, fileManager)

    @Test
    fun testInfoRequest() {
        assertEquals("Информация о коллекции:\n" +
                " Тип коллекции: " + collectionManager.getCollectionType() + "\n" +
                " Размер коллекции: " + collectionManager.getCollectionSize() + "\n" +
                " Дата и время последнего сохранения: " + collectionManager.getLastSaveTime() + "\n" +
                " Дата и время последней иницилизации: " + collectionManager.getLastInitTime(),
            requestManager.infoRequest(""))
    }
}