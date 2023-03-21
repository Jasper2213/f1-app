package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.R
import be.howest.jasperdesnyder.formulaone.model.Race

@Composable
fun RaceDetailScreen(
    selectedRace: Race,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp)
            ) {
                Column(
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Text(
                        text = stringResource(selectedRace.title),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                    Text(
                        text = selectedRace.date,
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
                Image(
                    painter = painterResource(selectedRace.trackLayoutRes),
                    contentDescription = stringResource(selectedRace.title) + " track layout"
                )
            }

            Text(
                text = "Race results",
                fontSize = 26.sp,
                fontStyle = FontStyle.Italic
            )
            LazyColumn(
                modifier = Modifier
                    .height(300.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black
                    )
            ) {
                itemsIndexed(selectedRace.results) { index, result ->
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
            }
        }
    }
}