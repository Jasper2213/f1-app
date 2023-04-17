package be.howest.jasperdesnyder.formulaone.repositories

import be.howest.jasperdesnyder.formulaone.FormulaOneScreen
import be.howest.jasperdesnyder.formulaone.model.BottomNavItem
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.BalanceScaleLeftSolid
import compose.icons.lineawesomeicons.BarsSolid
import compose.icons.lineawesomeicons.Calendar
import compose.icons.lineawesomeicons.HomeSolid
import compose.icons.lineawesomeicons.ListOlSolid

object NavItemsRepo {
    val items = listOf(
        BottomNavItem(
            label = "Predictions",
            icon = LineAwesomeIcons.BalanceScaleLeftSolid,
            route = FormulaOneScreen.Predictions
        ),
        BottomNavItem(
            label = "Standings",
            icon = LineAwesomeIcons.ListOlSolid,
            route = FormulaOneScreen.DriverStandings
        ),
        BottomNavItem(
            label = "Home",
            icon = LineAwesomeIcons.HomeSolid,
            route = FormulaOneScreen.Start
        ),
        BottomNavItem(
            label = "Calendar",
            icon = LineAwesomeIcons.Calendar,
            route = FormulaOneScreen.Calendar
        ),
        BottomNavItem(
            label = "Menu",
            icon = LineAwesomeIcons.BarsSolid,
            route = FormulaOneScreen.Menu
        )
    )
}
