package utility

import commands.*
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class HelpTest {
    private var testCommandList = LinkedHashMap<String, AbsctractCommand>()
    private var testList = ""
    private val commandExecuter = CommandExecuter(fabrique = Fabrique(Scanner(System.`in`)))

    init {
        testCommandList["help"] = Help()
        testCommandList["info"] = Info()
        testCommandList["show"] = Show()
        testCommandList["add"] = Add()
        testCommandList["update"] = UpdateById()
        testCommandList["remove_by_id"] = RemoveById()
        testCommandList["clear"] = Clear()
        testCommandList["save"] = Save()
        testCommandList["execute_script"] = ExecuteScript()
        testCommandList["execute_script -delecate"] = ExecuteScriptDelecate()
        testCommandList["exit"] = Exit()
        testCommandList["add_if_min"] = AddIfMinOscarsCount()
        testCommandList["remove_greater"] = RemoveGreaterCountOfOscars()
        testCommandList["remove_lower"] = RemoveLowerCountOfOscars()
        testCommandList["average_of_oscars_count"] = CountAvarageOfOscars()
        testCommandList["count_greater_than_genre"] = CountGenresGreater()
        testCommandList["print_field_descending_oscars_count"] = PrintOscarsCountsInDescending()

        for (command in testCommandList) {
            testList += command.value.getName() + ": " + command.value.getExplanationOfCommand() + "\n"
        }
    }

    @Test
    fun testHelp() {
        assertEquals(testList.substring(0, testList.length - 2), commandExecuter.help())
    }
}