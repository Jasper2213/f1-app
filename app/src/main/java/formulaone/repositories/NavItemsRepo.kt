package formulaone.repositories

import formulaone.FormulaOneScreen
import be.howest.jasperdesnyder.formulaone.R
import formulaone.model.BottomNavItem
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.BalanceScaleLeftSolid
import compose.icons.lineawesomeicons.BarsSolid
import compose.icons.lineawesomeicons.Calendar
import compose.icons.lineawesomeicons.HomeSolid
import compose.icons.lineawesomeicons.ListOlSolid

object NavItemsRepo {
    val items = listOf(
        BottomNavItem(
            label = R.string.nav_predictions,
            icon = LineAwesomeIcons.BalanceScaleLeftSolid,
            route = FormulaOneScreen.Predictions
        ),
        BottomNavItem(
            label = R.string.nav_standings,
            icon = LineAwesomeIcons.ListOlSolid,
            route = FormulaOneScreen.DriverStandings
        ),
        BottomNavItem(
            label = R.string.nav_home,
            icon = LineAwesomeIcons.HomeSolid,
            route = FormulaOneScreen.Start
        ),
        BottomNavItem(
            label = R.string.nav_calendar,
            icon = LineAwesomeIcons.Calendar,
            route = FormulaOneScreen.Calendar
        ),
        BottomNavItem(
            label = R.string.nav_menu,
            icon = LineAwesomeIcons.BarsSolid,
            route = FormulaOneScreen.Menu
        )
    )
}
