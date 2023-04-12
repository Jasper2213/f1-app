package be.howest.jasperdesnyder.formulaone.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.model.FormulaOneUiState
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneViewModel

@Composable
fun MenuScreen(
    onNextRaceClicked: () -> Unit,
    onDriversStandingsClicked: () -> Unit,
    onConstructorsStandingsClicked: () -> Unit,
    onCalendarClicked: () -> Unit,
    onPredictionsClicked: () -> Unit,
    onWebsiteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp)
    ) {
        MenuItem(onClick = onNextRaceClicked, text = "Next race")
        MenuItem(onClick = onDriversStandingsClicked, text = "Drivers standings")
        MenuItem(onClick = onConstructorsStandingsClicked, text = "Constructors standings")
        MenuItem(onClick = onCalendarClicked, text = "Calendar")
        MenuItem(onClick = onPredictionsClicked, text = "Predictions")
        MenuItem(onClick = onWebsiteClicked, text = "Go to the official Formula 1© website")
    }
}

@Composable
private fun MenuItem(onClick: () -> Unit, text: String) {
    Card(
        elevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.small
            )
            .shadow(
                elevation = 100.dp,
                ambientColor = Color.Gray,
                spotColor = Color.Gray,
                shape = MaterialTheme.shapes.small
            )
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.small
            )
    ) {
        Text(
            text = text,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(15.dp)
        )
    }
}