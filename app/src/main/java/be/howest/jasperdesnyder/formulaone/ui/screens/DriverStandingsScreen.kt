package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import be.howest.jasperdesnyder.formulaone.model.Driver
import be.howest.jasperdesnyder.formulaone.repositories.DriverRepo

@Composable
fun DriverStandingsScreen(
    onConstructorStandingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StandingsSelector(
                text = "Drivers",
                isBold = true
            )
            StandingsSelector(
                text = "Constructors",
                onClick = onConstructorStandingsClicked,
                isBold = false
            )
        }

        Line(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 5.dp,
                    ambientColor = Color.Black,
                    spotColor = Color.Black,
                )
        )

        LazyColumn {
            itemsIndexed(DriverRepo.drivers) { index, driver ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp)
                ) {
                    DriverItem(
                        driver = driver,
                        number = index + 1
                    )
                }
            }
        }
    }
}

@Composable
private fun DriverItem(
    driver: Driver,
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
            .padding(vertical = 5.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
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
                    modifier = Modifier.padding(end = 20.dp)
                )

                Column {
                    Text(
                        text = driver.name,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = driver.teamName,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }

            Text(
                text = "${driver.points}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StandingsSelector(
    text: String,
    onClick: () -> Unit = {},
    isBold: Boolean
) {
    val boldState: FontWeight = if (isBold) {
        FontWeight.Bold
    } else {
        FontWeight.Normal
    }

    Text(
        text = text,
        textDecoration = TextDecoration.Underline,
        fontSize = 26.sp,
        fontWeight = boldState,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(10.dp)
    )
}

@Composable
fun Line(modifier: Modifier = Modifier) {
    Divider(
        color = Color.Black,
        thickness = 1.dp,
        modifier = modifier
    )
}