package be.howest.jasperdesnyder.formulaone.repositories

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import be.howest.jasperdesnyder.formulaone.FormulaOneScreen
import be.howest.jasperdesnyder.formulaone.R
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
