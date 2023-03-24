package be.howest.jasperdesnyder.formulaone.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
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
import be.howest.jasperdesnyder.formulaone.model.Race
import be.howest.jasperdesnyder.formulaone.model.Result

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RaceDetailScreen(
    selectedRace: Race,
    modifier: Modifier = Modifier
) {
    RaceDetailScreenContent(selectedRace)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun RaceDetailScreenContent(selectedRace: Race) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            GeneralRaceInformation(race = selectedRace)

            Text(
                text = "Race results",
                fontSize = 26.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 20.dp)
            )

//            if (selectedRace.results.isEmpty()) {
//                NoResults()
//            }
//            else {
//                LazyColumn(
//                    modifier = Modifier
//                        .height(300.dp)
//                        .border(
//                            width = 1.dp,
//                            color = Color.Black
//                        )
//                ) {
//                    itemsIndexed(selectedRace.results) { index, result ->
//                        RaceResultItem(result)
//                    }
//                }
//            }
        }
    }
}

@Composable
private fun NoResults() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_results),
            fontSize = 26.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun RaceResultItem(result: Result) {
    Row(
        modifier = Modifier
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
                modifier = Modifier.padding(end = 10.dp)
            )

            Column {
                Text(
                    text = result.driver,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = result.team,
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