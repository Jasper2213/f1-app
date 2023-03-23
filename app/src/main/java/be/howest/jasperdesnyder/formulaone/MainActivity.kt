package be.howest.jasperdesnyder.formulaone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import be.howest.jasperdesnyder.formulaone.ui.theme.FormulaOneTheme

// TODO: Add predictions screen/functionality (mostly done, still needs to check if prediction is correct, and add points if so)
// TODO: Implement API
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