package be.howest.jasperdesnyder.formulaone

import be.howest.jasperdesnyder.formulaone.data.getDayInWeekFromDate
import be.howest.jasperdesnyder.formulaone.data.getImageBasedOnName
import be.howest.jasperdesnyder.formulaone.data.getPrettyNumber
import be.howest.jasperdesnyder.formulaone.data.getTimeInLocalFormat
import be.howest.jasperdesnyder.formulaone.data.prettifyDate
import be.howest.jasperdesnyder.formulaone.data.prettifyRaceTitle
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FormulaOneTests {

    @Test
    fun getImageBasedOnCircuitName() {
        val circuitName = "spa"

        val image = getImageBasedOnName(circuitName)

        assertEquals(image, R.drawable.belgium)
    }

    @Test
    fun getImageBasedOnCircuitNameThatDoesNotExist() {
        val circuitName = "doesnotexist"

        val image = getImageBasedOnName(circuitName)

        assertEquals(image, R.drawable.no_image)
    }

    @Test
    fun prettifyDate() {
        val firstDate = "2023-05-05"
        val secondDate = "2023-05-07"
        val expectedPattern = Regex(pattern = "([0-9]+(/[0-9]+)+)\\s-\\s([0-9]+(/[0-9]+)+)")

        val prettyDate = prettifyDate(firstDate, secondDate)

        assertTrue(expectedPattern.matches(prettyDate))
    }

    @Test
    fun getDayOfWeekFromDate() {
        val date = "2023-05-05"
        val expectedDay = "Friday"

        val dateOfWeek = getDayInWeekFromDate(date)

        assertEquals(expectedDay, dateOfWeek)
    }

    @Test
    fun getTimeInLocalFormat() {
        val time = "19:30:00Z"
        val expectedTime = "21:30"

        val localTime = getTimeInLocalFormat(time)

        assertEquals(expectedTime, localTime)
    }

    @Test
    fun getPrettyNumberUnder10() {
        val number = 1
        val expectedResult = "01"

        val prettyNumber = getPrettyNumber(number)

        assertEquals(expectedResult, prettyNumber)
    }

    @Test
    fun getPrettyNumberOver10() {
        val number = 11
        val expectedResult = "11"

        val prettyNumber = getPrettyNumber(number)

        assertEquals(expectedResult, prettyNumber)
    }

    @Test
    fun prettifyRaceTitle() {
        val raceTitle = "red_bull_ring"
        val expectedResult = "Red Bull Ring"

        val prettyRaceTitle = prettifyRaceTitle(raceTitle)

        assertEquals(expectedResult, prettyRaceTitle)
    }
}