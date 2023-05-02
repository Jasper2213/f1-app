package be.howest.jasperdesnyder.formulaone.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.R
import be.howest.jasperdesnyder.formulaone.data.createCalendarIntent
import be.howest.jasperdesnyder.formulaone.data.createMapsIntent
import be.howest.jasperdesnyder.formulaone.data.getDayInWeekFromDate
import be.howest.jasperdesnyder.formulaone.data.getImageBasedOnName
import be.howest.jasperdesnyder.formulaone.data.getTimeInLocalFormat
import be.howest.jasperdesnyder.formulaone.data.prettifyDate
import be.howest.jasperdesnyder.formulaone.data.prettifyRaceTitle
import be.howest.jasperdesnyder.formulaone.model.Race
import be.howest.jasperdesnyder.formulaone.model.Session
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneApiUiState
import java.time.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    formulaOneApiUiState: FormulaOneApiUiState
) {
    StartScreenContent((formulaOneApiUiState as FormulaOneApiUiState.Success).nextRace)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun StartScreenContent(
    race: Race,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(modifier = modifier.align(Alignment.Center)) {
            GeneralRaceInformation(race)
            GenerateSessions(race)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GeneralRaceInformation(race: Race, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                modifier = modifier.padding(end = 10.dp)
            ) {
                Text(
                    text = prettifyRaceTitle(race.circuit?.circuitId!!),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
                Text(
                    text = prettifyDate(race.firstPractice?.date!!, race.date!!),
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(getImageBasedOnName(race.circuit?.circuitId!!)),
                    contentDescription = race.raceName!! + " track layout"
                )
                Text(
                    text = "Show on map",
                    modifier = modifier.clickable {
                        createMapsIntent(race, context)
                    },
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        Button(
            onClick = {
                createCalendarIntent(race, context)
            },
            modifier = modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Add race to calendar"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun GenerateSessions(race: Race) {
    if (race.sprint == null) GenerateNormalSessions(race)
    else GenerateSprintSessions(race)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun GenerateNormalSessions(race: Race) {
    Session(
        title = stringResource(R.string.practice_1),
        session = race.firstPractice!!
    )

    Session(
        title = stringResource(R.string.practice_2),
        session = race.secondPractice!!
    )

    Session(
        title = stringResource(R.string.practice_3),
        session = race.thirdPractice!!
    )

    Session(
        title = stringResource(R.string.qualifying),
        session = race.qualifying!!
    )

    Session(
        title = stringResource(R.string.race),
        race = race
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun GenerateSprintSessions(race: Race) {
    Session(
        title = stringResource(R.string.practice_1),
        session = race.firstPractice!!
    )

    Session(
        title = stringResource(R.string.qualifying),
        session = race.qualifying!!
    )

    Session(
        title = stringResource(R.string.practice_2),
        session = race.secondPractice!!
    )

    Session(
        title = stringResource(R.string.sprint),
        session = race.sprint!!
    )

    Session(
        title = stringResource(R.string.race),
        race = race
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Session(
    title: String,
    modifier: Modifier = Modifier,
    session: Session? = null,
    race: Race? = null
) {
    val time: String
    val date: String

    when (race) {
        null -> {
            time = getTimeInLocalFormat(session?.time!!)
            date = getDayInWeekFromDate(session.date!!)
        }

        else -> {
            time = getTimeInLocalFormat(race.time!!)
            date = getDayInWeekFromDate(race.date!!)
        }
    }

    Column(
        modifier = modifier.padding(vertical = 10.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = date,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )

            Text(
                text = time,
                fontSize = 24.sp
            )
        }
    }
}