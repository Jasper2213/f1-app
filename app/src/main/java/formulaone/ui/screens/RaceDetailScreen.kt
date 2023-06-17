package formulaone.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.R
import formulaone.model.Race
import formulaone.model.Results

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RaceDetailScreen(
    selectedRace: Race
) {
    RaceDetailScreenContent(selectedRace)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun RaceDetailScreenContent(selectedRace: Race, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(modifier = modifier.align(Alignment.Center)) {
            GeneralRaceInformation(race = selectedRace)

            Text(
                text = "Race results",
                fontSize = 26.sp,
                fontStyle = FontStyle.Italic,
                modifier = modifier.padding(top = 20.dp)
            )

            if (selectedRace.results.isEmpty()) {
                NoResults()
            }
            else {
                LazyColumn(
                    modifier = modifier
                        .height(400.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = MaterialTheme.shapes.small
                        )
                ) {
                    itemsIndexed(selectedRace.results) { index, result ->
                        Row(
                            modifier = modifier
                                .background(
                                    color = if (index % 2 == 0) Color.LightGray else Color.White
                                )
                        ) {
                            RaceResultItem(result)
                        }

                        if (index < selectedRace.results.size - 1)
                            Divider(color = Color.Black, thickness = 1.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun NoResults(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_results),
            fontSize = 26.sp,
            color = Color.Gray,
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun RaceResultItem(
    result: Results,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = result.position.toString(),
                textDecoration = TextDecoration.Underline,
                modifier = modifier.padding(end = 10.dp)
            )

            Column {
                Text(
                    text = result.driver?.firstName + " " + result.driver?.lastName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = result.constructor?.name!!,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        Text(
            text = "+${result.points}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}