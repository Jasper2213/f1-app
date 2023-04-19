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
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.data.StandingsTopBar
import be.howest.jasperdesnyder.formulaone.data.getPrettyNumber
import be.howest.jasperdesnyder.formulaone.model.DriverStanding
import be.howest.jasperdesnyder.formulaone.model.StandingsTable
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneApiUiState

@Composable
fun DriverStandingsScreen(
    formulaOneApiUiState: FormulaOneApiUiState,
    onConstructorStandingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        StandingsTopBar(
            onConstructorStandingsClicked = onConstructorStandingsClicked,
            driversSelected = true
        )

        DriverStandingsScreenContent((formulaOneApiUiState as FormulaOneApiUiState.Success).formulaOneData.standingsTable)
    }
}

@Composable
private fun DriverStandingsScreenContent(standingsTable: StandingsTable?, modifier: Modifier = Modifier) {
    LazyColumn {
        itemsIndexed(standingsTable?.standingsLists!![0].driverStanding) { index, driverStanding ->
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                DriverItem(
                    driverStanding = driverStanding,
                    number = index + 1
                )
            }
        }
    }
}

@Composable
private fun DriverItem(
    driverStanding: DriverStanding,
    number: Int,
    modifier: Modifier = Modifier
) {
    val driver = driverStanding.driver

    val prettyNumber = getPrettyNumber(number)

    Card(
        modifier = modifier
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
                shape = MaterialTheme.shapes.small
            )
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.small
            ),
        elevation = 5.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = prettyNumber,
                    fontSize = 22.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = modifier.padding(end = 20.dp)
                )

                Column {
                    Text(
                        text = driver?.firstName + " " + driver?.lastName,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = driverStanding.constructors[0].name!!,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }

            Text(
                text = driverStanding.points.toString(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Line(modifier: Modifier = Modifier) {
    Divider(
        color = Color.Black,
        thickness = 1.dp,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 5.dp,
                ambientColor = Color.Black,
                spotColor = Color.Black,
            )
    )
}