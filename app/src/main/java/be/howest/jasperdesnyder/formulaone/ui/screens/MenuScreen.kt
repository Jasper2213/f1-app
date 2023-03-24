package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        MenuItem(onClick = onWebsiteClicked, text = "Go to official Formula 1© website")
    }
}

@Composable
private fun MenuItem(onClick: () -> Unit, text: String) {
    Card(
        elevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
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