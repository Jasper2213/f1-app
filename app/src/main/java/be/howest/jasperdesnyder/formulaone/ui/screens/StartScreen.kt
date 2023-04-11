package be.howest.jasperdesnyder.formulaone.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
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
import androidx.core.content.ContextCompat.startActivity
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
    StartScreenContent((formulaOneApiUiState as FormulaOneApiUiState.Success).nextRace)
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
    val context = LocalContext.current

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    text = race.circuit?.circuitId!!.replace("_", " ")
                        .replaceFirstChar { it.uppercase() },
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
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(getImageBasedOnName(race.circuit?.circuitId!!)),
                    contentDescription = race.raceName!! + " track layout"
                )
                Text(
                    text = "Show on map",
                    modifier = Modifier.clickable {
                        val long = race.circuit?.Location?.long
                        val lat = race.circuit?.Location?.lat
                        val circuitName = race.circuit?.circuitName

                        val gmmIntentUri = Uri.parse("geo:$lat,$long?q=$circuitName")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(context, mapIntent, null)
                    },
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        Button(
            onClick = {
                val city = race.circuit?.Location?.locality
                val country = race.circuit?.Location?.country

                val dateParts = race.date!!.split("-")
                val year = dateParts[0].toInt()
                val month = dateParts[1].toInt()
                val day = dateParts[2].toInt()

                val timeParts = getTimeInLocalFormat(race.time!!).split(":")
                val hour = timeParts[0].toInt()
                val minutes = timeParts[1].toInt()

                val startTime: Long = Calendar.getInstance().run {
                    set(year, month - 1, day, hour, minutes)
                    timeInMillis
                }
                val endTime: Long = Calendar.getInstance().run {
                    set(year, month - 1, day, hour + 2, minutes)
                    timeInMillis
                }

                val intent = Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.Events.TITLE, race.raceName)
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "$city, $country")
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                    .putExtra(CalendarContract.Events.ALL_DAY, false)

                startActivity(context, intent, null)
            },
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Add race to calendar"
            )
        }
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