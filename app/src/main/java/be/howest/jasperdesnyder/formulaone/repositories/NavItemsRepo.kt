package be.howest.jasperdesnyder.formulaone.repositories

import be.howest.jasperdesnyder.formulaone.FormulaOneScreen
import be.howest.jasperdesnyder.formulaone.model.BottomNavItem
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.*

object NavItemsRepo {
    val items = listOf(
        BottomNavItem(
            label = "Predictions",
            icon = LineAwesomeIcons.BalanceScaleLeftSolid,
            route = FormulaOneScreen.Predictions
        ),
        BottomNavItem(
            label = "Calendar",
            icon = LineAwesomeIcons.Calendar,
            route = FormulaOneScreen.Calendar
        ),
        BottomNavItem(
            label = "Home",
            icon = LineAwesomeIcons.HomeSolid,
            route = FormulaOneScreen.Start
        ),
        BottomNavItem(
            label = "Standings",
            icon = LineAwesomeIcons.ListOlSolid,
            route = FormulaOneScreen.DriverStandings
        ),
        BottomNavItem(
            label = "Menu",
            icon = LineAwesomeIcons.AlignJustifySolid,
            route = FormulaOneScreen.Menu
        )
    )
}