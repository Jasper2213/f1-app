package be.howest.jasperdesnyder.formulaone

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import be.howest.jasperdesnyder.formulaone.ui.theme.FormulaOneTheme

// TODO: Add predictions screen/functionality (mostly done, still needs to check if prediction is correct, and add points if so)
// TODO: Change fonts?
// TODO: Add custom app icon
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormulaOneTheme {
                FormulaOneApp()
            }
        }
    }
}