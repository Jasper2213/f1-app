package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import be.howest.jasperdesnyder.formulaone.model.Race

@Composable
fun RaceDetailScreen(
    selectedRace: Race,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            Image(
                painter = painterResource(id = selectedRace.trackLayoutRes),
                contentDescription = "Track layout"
            )
        }
        Text(text = selectedRace.title)
        Text(text = selectedRace.date)
    }
}