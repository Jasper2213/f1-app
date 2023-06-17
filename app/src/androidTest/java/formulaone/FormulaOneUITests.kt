package formulaone

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import be.howest.jasperdesnyder.formulaone.R
import formulaone.ui.theme.FormulaOneTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FormulaOneUITests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupFormulaOneNavHost() {
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            FormulaOneTheme {
                FormulaOneApp(navController = navController)
            }
        }
    }

    @Test
    fun formulaOneNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(FormulaOneScreen.Startup.name)
    }

    @Test
    fun formulaOneNavHost_verifyBackNavigationNotShownOnStartScreen() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun formulaOneNavHost_verifyBackNavigationShownOnOtherScreens() {
        navigateToStartScreen()
        navigateToCalendarScreen()
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertExists()
    }

    @Test
    fun formulaOneNavHost_navigateToCalendarScreen() {
        navigateToCalendarScreen()
        navController.assertCurrentRouteName(FormulaOneScreen.Calendar.name)
    }

    @Test
    fun formulaOneNavHost_navigateToDriverStandingsScreen() {
        navigateToDriverStandingsScreen()
        navController.assertCurrentRouteName(FormulaOneScreen.DriverStandings.name)
    }

    @Test
    fun formulaOneNavHost_navigateToConstructorStandingsScreen() {
        navigateToConstructorStandingsScreen()
        navController.assertCurrentRouteName(FormulaOneScreen.ConstructorStandings.name)
    }

    @Test
    fun formulaOneNavHost_navigateToMenuScreen() {
        navigateToMenuScreen()
        navController.assertCurrentRouteName(FormulaOneScreen.Menu.name)
    }

    @Test
    fun formulaOneNavHost_navigateToPredictionsScreen() {
        navigateToPredictionsScreen()
        navController.assertCurrentRouteName(FormulaOneScreen.Predictions.name)
    }

    @Test
    fun submitPrediction() {
        navigateToPredictionsScreen()
        resetPredictions()

        navController.assertCurrentRouteName(FormulaOneScreen.Predictions.name)

        composeTestRule.onNodeWithTag("dropdown").assertExists().performClick()
        composeTestRule.onNodeWithTag("Verstappen").assertExists().performClick()

        composeTestRule.onNodeWithTag("pointsTextField").assertExists().performTextInput("50")

        composeTestRule.onNodeWithTag("potentialPoints").assertTextEquals("60.0")

        composeTestRule.onNodeWithTag("submitButton").assertExists().assertIsEnabled().performClick()

        composeTestRule.onNodeWithTag("confirmButton").assertExists().performClick()

        composeTestRule.onNodeWithTag("submitButton").assertExists().assertIsNotEnabled()
    }

    @Test
    fun submitIncorrectPrediction() {
        navigateToPredictionsScreen()
        resetPredictions()

        navController.assertCurrentRouteName(FormulaOneScreen.Predictions.name)

        composeTestRule.onNodeWithTag("submitButton").assertExists().assertIsEnabled().performClick()

        composeTestRule.onNodeWithTag("errorDialog").assertExists()
        composeTestRule.onNodeWithTag("confirmErrorButton").assertExists().performClick()
        composeTestRule.onNodeWithTag("errorDialog").assertDoesNotExist()
    }

    private fun navigateToStartScreen() {
        navigateToScreen(R.string.nav_home)
    }

    private fun navigateToCalendarScreen() {
        navigateToScreen(R.string.nav_calendar)
    }

    private fun navigateToPredictionsScreen() {
        navigateToScreen(R.string.nav_predictions)
    }

    private fun navigateToDriverStandingsScreen() {
        navigateToScreen(R.string.nav_standings)
    }

    private fun navigateToConstructorStandingsScreen() {
        navigateToDriverStandingsScreen()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.constructors)).performClick()
    }

    private fun navigateToMenuScreen() {
        navigateToScreen(R.string.nav_menu)
    }

    private fun navigateToScreen(screen: Int) {
        composeTestRule.waitUntil(5_000) { navController.currentDestination?.route == FormulaOneScreen.Start.name }    // Wait until API is loaded
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(screen)).performClick()
    }

    private fun resetPredictions() {
        composeTestRule.onNodeWithTag("resetPredictionsButton").assertExists().performClick()
    }
}