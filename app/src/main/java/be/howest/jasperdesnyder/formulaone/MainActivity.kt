package be.howest.jasperdesnyder.formulaone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import be.howest.jasperdesnyder.formulaone.ui.theme.FormulaOneTheme

// TODO: Add images for circuits
// TODO: Find good icons
// TODO: Pick theme colors
// TODO: Change fonts?

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormulaOneTheme {
                FormulaOneApp()
            }
        }
    }
}