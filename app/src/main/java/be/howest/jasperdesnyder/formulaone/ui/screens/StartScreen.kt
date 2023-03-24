package be.howest.jasperdesnyder.formulaone.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import be.howest.jasperdesnyder.formulaone.ErrorScreen
import be.howest.jasperdesnyder.formulaone.LoadingScreen
import be.howest.jasperdesnyder.formulaone.R
import be.howest.jasperdesnyder.formulaone.model.Race
import be.howest.jasperdesnyder.formulaone.model.Session
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneApiUiState
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    formulaOneApiUiState: FormulaOneApiUiState,
    modifier: Modifier = Modifier
) {
    when (formulaOneApiUiState) {
        is FormulaOneApiUiState.Loading -> LoadingScreen()
        is FormulaOneApiUiState.Error -> ErrorScreen()
        is FormulaOneApiUiState.Success -> StartScreenContent(formulaOneApiUiState.nextRace)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun StartScreenContent(race: Race) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            GeneralRaceInformation(race)
            GenerateSessions(race)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GeneralRaceInformation(race: Race) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(
                text = race.Circuit?.circuitId!!.replace("_", " ").replaceFirstChar { it.uppercase() },
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            Text(
                text = prettifyDate(race.FirstPractice?.date!!, race.date!!),
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )
        }
        Image(
            painter = painterResource(getImageBasedOnName(race.Circuit?.circuitId!!)),
            contentDescription = race.raceName!! + " track layout"
        )
    }
}

fun getImageBasedOnName(name: String): Int {
    return when (name) {
        "bahrain" -> R.drawable.bahrain
        "jeddah" -> R.drawable.saudi_arabia
        "albert_park" -> R.drawable.australia
        "baku" -> R.drawable.azerbaijan
        "miami" -> R.drawable.miami
        "imola" -> R.drawable.imola
        "monaco" -> R.drawable.monaco
        "catalunya" -> R.drawable.spain
        "villeneuve" -> R.drawable.canada
        "red_bull_ring" -> R.drawable.austria
        "silverstone" -> R.drawable.britain
        "hungaroring" -> R.drawable.hungary
        "spa" -> R.drawable.belgium
        "zandvoort" -> R.drawable.netherlands
        "monza" -> R.drawable.monza
        "marina_bay" -> R.drawable.singapore
        "suzuka" -> R.drawable.japan
        "losail" -> R.drawable.qatar
        "americas" -> R.drawable.united_states
        "rodriguez" -> R.drawable.mexico
        "interlagos" -> R.drawable.brazil
        "vegas" -> R.drawable.las_vegas
        "yas_marina" -> R.drawable.abu_dhabi
        else -> R.drawable.bahrain
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun prettifyDate(firstDate: String, lastDate: String): String {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val first = LocalDate.parse(firstDate)
    val last = LocalDate.parse(lastDate)
    return first.format(formatter) + " - " + last.format(formatter)
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
        session = race.FirstPractice!!
    )

    Session(
        title = stringResource(R.string.practice_2),
        session = race.SecondPractice!!
    )

    Session(
        title = stringResource(R.string.practice_3),
        session = race.ThirdPractice!!
    )

    Session(
        title = stringResource(R.string.qualifying),
        session = race.Qualifying!!
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
        session = race.FirstPractice!!
    )

    Session(
        title = stringResource(R.string.qualifying),
        session = race.Qualifying!!
    )

    Session(
        title = stringResource(R.string.practice_2),
        session = race.SecondPractice!!
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
private fun getDateFromSession(date: String): String {
    return LocalDate.parse(date).dayOfWeek.toString().lowercase()
        .replaceFirstChar { it.uppercase() }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getTimeInLocalFormat(time: String): String {
    val original = LocalTime.parse(time, DateTimeFormatter.ISO_OFFSET_TIME)
    val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                                     .withLocale(Locale.Builder().setLanguageTag("be").build())

    return formatter.format(original.plusHours(2))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Session(
    title: String,
    session: Session? = null,
    race: Race? = null
) {
    val time: String
    val date: String

    when (race) {
        null -> {
            time = getTimeInLocalFormat(session?.time!!)
            date = getDateFromSession(session.date!!)
        }
        else -> {
            time = getTimeInLocalFormat(race.time!!)
            date = getDateFromSession(race.date!!)
        }
    }

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