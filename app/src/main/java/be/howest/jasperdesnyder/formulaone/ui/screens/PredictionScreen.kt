package be.howest.jasperdesnyder.formulaone.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import be.howest.jasperdesnyder.formulaone.R
import be.howest.jasperdesnyder.formulaone.data.FormulaOneUiState
import be.howest.jasperdesnyder.formulaone.model.Driver
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneApiUiState
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneViewModel

// TODO: Add text when predictions are disabled?

@Composable
fun PredictionScreen(
    formulaOneApiUiState: FormulaOneApiUiState,
    uiState: FormulaOneUiState,
    onSubmitClicked: () -> Unit,
    viewModel: FormulaOneViewModel
) {
    PredictionScreenContent(
        uiState,
        viewModel,
        onSubmitClicked,
        (formulaOneApiUiState as FormulaOneApiUiState.Success).formulaOneData.standingsTable?.standingsLists!![0].driverStanding.map { it.driver!! }
    )
}

@Composable
private fun PredictionScreenContent(
    uiState: FormulaOneUiState,
    viewModel: FormulaOneViewModel,
    onSubmitClicked: () -> Unit,
    drivers: List<Driver>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDriver by rememberSaveable { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    var usedPoints by rememberSaveable { mutableStateOf(0.0) }

    var showConfirmDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
                else Icons.Filled.KeyboardArrowDown

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Current amount of points
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.current_amount_of_points),
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
            Text(
                text = uiState.availablePoints.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        // Select driver
        Column {
            Text(
                text = "Who will win the ${(viewModel.formulaOneApiUiState as FormulaOneApiUiState.Success).nextRace.raceName}?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = uiState.selectedDriver!!.ifEmpty { selectedDriver },
                onValueChange = { selectedDriver = it },
                modifier = modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize =
                            coordinates.size.toSize()  // Makes the dropdown menu the same width as the text field
                    },
                label = {
                    Text(
                        stringResource(R.string.select_driver),
                        fontSize = 18.sp
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "contentDescription",
                        modifier = modifier.clickable { expanded = !expanded }.testTag("dropdown"))
                },
                readOnly = true,
                enabled = uiState.predictionsEnabled
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                    .height(300.dp)
            ) {
                drivers.forEachIndexed { index, driver ->
                    DropdownMenuItem(
                        onClick = {
                            selectedDriver = driver.firstName + " " + driver.lastName
                            expanded = false
                        },
                        modifier = modifier
                            .background(
                                color = if (index % 2 == 0) Color.LightGray else Color.White
                            ).testTag(driver.lastName!!)
                    ) {
                        Text(
                            text = driver.firstName + " " + driver.lastName,
                            color = MaterialTheme.colors.onSecondary
                        )
                    }
                    if (index < drivers.size - 1)
                        Divider(color = Color.Black, thickness = 1.dp)
                }
            }
        }

        // Points to use
        Column {
            Text(
                text = stringResource(R.string.points_to_use),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = if (!uiState.predictionsEnabled) uiState.usedPoints.toString()
                        else if (usedPoints == 0.0) ""
                        else usedPoints.toString(),
                onValueChange = {
                    usedPoints =
                        if (it.isNotEmpty()) {
                            it.toDoubleOrNull() ?: usedPoints
                        } else {
                            0.0
                        }
                    viewModel.updateUsedPoints(usedPoints)
                },
                label = {
                    Text(
                        stringResource(R.string.enter_points),
                        fontSize = 16.sp
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = modifier.fillMaxWidth().testTag("pointsTextField"),
                enabled = uiState.predictionsEnabled
            )
        }

        // Potential points to win
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.potential_points_to_win),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = (uiState.usedPoints * 1.2).toString(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = {
                if (usedPoints != 0.0 &&
                    selectedDriver.isNotEmpty() &&
                    usedPoints <= uiState.availablePoints
                ) {
                    showConfirmDialog = true
                } else showErrorDialog = true
            },
            modifier = modifier.fillMaxWidth().testTag("submitButton"),
            enabled = uiState.predictionsEnabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                    if (uiState.predictionsEnabled) MaterialTheme.colors.primary
                    else Color.Gray
            )
        ) {
            val buttonText: String = if (uiState.predictionsEnabled) stringResource(R.string.submit)
                                     else "Prediction already submitted"
            Text(
                text = buttonText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirm prediction?") },
            text = { Text("This cannot be undone!") },
            confirmButton = {
                Button(onClick = {
                    if (usedPoints != 0.0 &&
                        selectedDriver.isNotEmpty() &&
                        usedPoints <= uiState.availablePoints
                    ) {
                        viewModel.updatePredictionsEnabled(false)
                        viewModel.updateRacePredictedOn((viewModel.formulaOneApiUiState as FormulaOneApiUiState.Success).nextRace.raceName!!)
                        viewModel.updateUsedPoints(usedPoints)
                        viewModel.updateAvailablePoints()
                        viewModel.updatePredictedDriver(selectedDriver)
                    }

                    onSubmitClicked()

                    showConfirmDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                }) {
                    Text("Dismiss")
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text("Please fill in all the necessary fields, and make sure you have enough points!") },
            confirmButton = {
                TextButton(onClick = {
                    showErrorDialog = false
                }) {
                    Text("Close")
                }
            }
        )
    }
}