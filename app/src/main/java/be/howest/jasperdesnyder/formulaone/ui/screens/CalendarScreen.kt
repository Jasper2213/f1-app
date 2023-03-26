package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.ErrorScreen
import be.howest.jasperdesnyder.formulaone.LoadingScreen
import be.howest.jasperdesnyder.formulaone.model.Race
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneApiUiState
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneViewModel

@Composable
fun CalendarScreen(
    formulaOneApiUiState: FormulaOneApiUiState,
    viewModel: FormulaOneViewModel,
    onRaceClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (formulaOneApiUiState) {
        is FormulaOneApiUiState.Loading -> LoadingScreen()
        is FormulaOneApiUiState.Error -> ErrorScreen()
        is FormulaOneApiUiState.Success -> CalendarScreenContent(formulaOneApiUiState.races, viewModel, onRaceClicked)
    }
}

@Composable
private fun CalendarScreenContent(
    races: List<Race>,
    viewModel: FormulaOneViewModel,
    onRaceClicked: () -> Unit
) {
    LazyColumn {
        itemsIndexed(races) { index, race ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
                    .clickable(onClick = {
                        viewModel.updateSelectedRace(race)
                        onRaceClicked()
                    })
            ) {
                RaceItem(
                    title = race.Circuit?.circuitId!!,
                    date = race.date!!,
                    trackLayoutRes = getImageBasedOnName(race.Circuit?.circuitId!!)
                )
            }
        }
    }
}

@Composable
private fun RaceItem(
    title: String,
    date: String,
    trackLayoutRes: Int,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(vertical = 5.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.small
            )
            .shadow(
                elevation = 100.dp,
                ambientColor = Color.Gray,
                spotColor = Color.Gray,
                shape = MaterialTheme.shapes.small)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.small
            )
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(15.dp)
        ) {
           Column (
               verticalArrangement = Arrangement.Center,
               modifier = Modifier.fillMaxHeight()
           ) {
               Text(
                   text = date,
                   fontSize = 24.sp
               )

               Text(
                   text = title.replace("_", " ").replaceFirstChar { it.uppercase() },
                   fontSize = 30.sp,
                   fontWeight = FontWeight.Bold
               )
           }

            Image (
                painter = painterResource(trackLayoutRes),
                contentDescription = "$title GP"
            )
        }
    }
}