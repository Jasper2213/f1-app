package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.model.ConstructorStandings
import be.howest.jasperdesnyder.formulaone.model.StandingsTable
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneApiUiState

@Composable
fun ConstructorStandingsScreen(
    formulaOneApiUiState: FormulaOneApiUiState,
    onDriverStandingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandingsTopBar(
            onDriverStandingsClicked = onDriverStandingsClicked,
            driversSelected = false
        )

        ConstructorStandingsScreenContent((formulaOneApiUiState as FormulaOneApiUiState.Success).formulaOneData.standingsTable)
    }
}

@Composable
private fun ConstructorStandingsScreenContent(standingsTable: StandingsTable?) {
    LazyColumn {
        itemsIndexed(standingsTable?.standingsLists!![0].constructorStandings) { index, constructorStanding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                ConstructorItem(
                    constructorStandings = constructorStanding,
                    number = index + 1
                )
            }
        }
    }
}

@Composable
private fun ConstructorItem(
    constructorStandings: ConstructorStandings,
    number: Int,
    modifier: Modifier = Modifier
) {
    val prettyNumber = if (number < 10) {
        "0$number"
    } else {
        "$number"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
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
            ),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = prettyNumber,
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(end = 10.dp)
                )

                Text(
                    text = constructorStandings.constructor?.name!!,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }

            Text(
                text = constructorStandings.points!!,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
