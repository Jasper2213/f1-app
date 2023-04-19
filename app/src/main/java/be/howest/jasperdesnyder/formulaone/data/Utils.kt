package be.howest.jasperdesnyder.formulaone.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import be.howest.jasperdesnyder.formulaone.R
import be.howest.jasperdesnyder.formulaone.model.Race
import be.howest.jasperdesnyder.formulaone.ui.screens.Line
import be.howest.jasperdesnyder.formulaone.workers.NotificationWorker
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
fun getMillisToNextRace(uiState: FormulaOneUiState): Long {
    val nextRace = uiState.nextRace!!
    val timeOfNextRace = getTimeInLocalFormat(nextRace.time!!)
    val dateOfNextRace = nextRace.date!!

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val dateTime = LocalDateTime.parse("$dateOfNextRace $timeOfNextRace", formatter)
    val timeToNextRace = dateTime.toInstant(ZoneOffset.UTC)
        .minus(
            2,
            ChronoUnit.HOURS
        )     // Minus 2 hours because time zones are not taken into account
        .minus(30, ChronoUnit.MINUTES)
        .toEpochMilli()

    return timeToNextRace - System.currentTimeMillis()
}

fun queueNotification(workManager: WorkManager, timeToNextRace: Long) {
    val notificationWorkRequest =
        OneTimeWorkRequestBuilder<NotificationWorker>()       // Only show notification if user opted in for it
            .setInitialDelay(
                1000 * 30/*timeToNextRace*/,
                TimeUnit.MILLISECONDS
            )
            .addTag("notification_before_race")
            .build()

    workManager.enqueue(notificationWorkRequest)
}

fun createMapsIntent(
    race: Race,
    context: Context
) {
    val long = race.circuit?.Location?.long
    val lat = race.circuit?.Location?.lat
    val circuitName = race.circuit?.circuitName

    val gmmIntentUri = Uri.parse("geo:$lat,$long?q=$circuitName")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    ContextCompat.startActivity(context, mapIntent, null)
}

@RequiresApi(Build.VERSION_CODES.O)
fun createCalendarIntent(
    race: Race,
    context: Context
) {
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

    ContextCompat.startActivity(context, intent, null)
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
fun prettifyDate(firstDate: String, lastDate: String): String {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val first = LocalDate.parse(firstDate)
    val last = LocalDate.parse(lastDate)
    return first.format(formatter) + " - " + last.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateFromSession(date: String): String {
    return LocalDate.parse(date).dayOfWeek.toString().lowercase()
        .replaceFirstChar { it.uppercase() }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeInLocalFormat(time: String): String {
    val original = LocalTime.parse(time, DateTimeFormatter.ISO_OFFSET_TIME)
    val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(Locale.Builder().setLanguageTag("be").build())

    return formatter.format(original.plusHours(2))
}

fun getPrettyNumber(number: Int) = if (number < 10) "0$number" else "$number"

@Composable
fun StandingsTopBar(
    onDriverStandingsClicked: () -> Unit = {},
    onConstructorStandingsClicked: () -> Unit = {},
    driversSelected: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StandingsSelector(
            text = "Drivers",
            onClick = onDriverStandingsClicked,
            isBold = driversSelected
        )
        StandingsSelector(
            text = "Constructors",
            onClick = onConstructorStandingsClicked,
            isBold = !driversSelected
        )
    }

    Line()
}

@Composable
fun StandingsSelector(
    text: String,
    onClick: () -> Unit = {},
    isBold: Boolean
) {
    val boldState: FontWeight =
        if (isBold) FontWeight.Bold else FontWeight.Normal

    Text(
        text = text,
        textDecoration = TextDecoration.Underline,
        fontSize = 26.sp,
        fontWeight = boldState,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(10.dp)
    )
}

fun prettifyRaceTitle(title: String) = title.replace("_", " ").replaceFirstChar { it.uppercase() }