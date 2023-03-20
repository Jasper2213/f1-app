package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.model.Race
import be.howest.jasperdesnyder.formulaone.repositories.RaceRepo
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneViewModel

@Composable
fun CalendarScreen(
    viewModel: FormulaOneViewModel,
    onRaceClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn {
        itemsIndexed(RaceRepo.races) { index, race ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
                    .clickable(onClick = {
                        viewModel.setSelectedRace(race)
                        onRaceClicked()
                    })
            ) {
                RaceItem(
                    title = race.title,
                    date = race.date,
                    trackLayoutRes = race.trackLayoutRes
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
            .padding(vertical = 5.dp),
        elevation = 10.dp
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
                   text = title,
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