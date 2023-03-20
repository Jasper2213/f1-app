package be.howest.jasperdesnyder.formulaone.model

import androidx.compose.ui.graphics.vector.ImageVector
import be.howest.jasperdesnyder.formulaone.FormulaOneScreen

data class BottomNavItem (
    val label: String,
    val icon: ImageVector,
    val route: FormulaOneScreen
)