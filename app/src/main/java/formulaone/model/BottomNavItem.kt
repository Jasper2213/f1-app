package formulaone.model

import androidx.compose.ui.graphics.vector.ImageVector
import formulaone.FormulaOneScreen

data class BottomNavItem(
    val label: Int,
    val icon: ImageVector,
    val route: FormulaOneScreen
)