package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.model.Race
import be.howest.jasperdesnyder.formulaone.repositories.RaceRepo
import kotlin.random.Random

@Composable
fun StartScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            GeneralRaceInformation()

            Session(
                title = "Practice 1",
                day = "Friday",
                startTime = "14:30",
                endTime = "15:30"
            )
            Session(
                title = "Practice 2",
                day = "Friday",
                startTime = "18:00",
                endTime = "19:00"
            )
            Session(
                title = "Practice 3",
                day = "Saturday",
                startTime = "14:30",
                endTime = "15:30"
            )
            Session(
                title = "Qualifying",
                day = "Saturday",
                startTime = "18:00",
                endTime = "19:00"
            )
            Session(
                title = "Race",
                day = "Sunday",
                startTime = "18:00"
            )
        }
    }
}

@Composable
fun GeneralRaceInformation(race: Race = RaceRepo.races[Random.nextInt(RaceRepo.races.size)]) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(
                text = stringResource(race.title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            Text(
                text = race.date,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )
        }
        Image(
            painter = painterResource(race.trackLayoutRes),
            contentDescription = stringResource(race.title) + " track layout"
        )
    }
}

@Composable
private fun Session(
    title: String,
    day: String,
    startTime: String,
    endTime: String? = null
) {
    Column(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = day,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )

            if (endTime != null)
                Text(
                    text = "$startTime - $endTime",
                    fontSize = 24.sp
                )
            else Text(
                text = startTime,
                fontSize = 24.sp
            )
        }
    }
}