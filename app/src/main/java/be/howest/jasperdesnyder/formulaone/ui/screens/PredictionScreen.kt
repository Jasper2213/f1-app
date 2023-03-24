package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import be.howest.jasperdesnyder.formulaone.R
import be.howest.jasperdesnyder.formulaone.model.FormulaOneUiState
import be.howest.jasperdesnyder.formulaone.repositories.DriverRepo
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneApiUiState
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneViewModel

// TODO: Figure out if I can keep uistate after app has been closed (to block the user from making a prediction if he already has)

@Composable
fun PredictionScreen(
    formulaOneApiUiState: FormulaOneApiUiState,
    uiState: FormulaOneUiState,
    onSubmitClicked: () -> Unit,
    viewModel: FormulaOneViewModel,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDriver by rememberSaveable { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    var usedPoints by rememberSaveable { mutableStateOf(0.0) }

    var showDialog by remember { mutableStateOf(false) }

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
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

        Column {
            Text(
                text = "Who will win the ${uiState.nextRace?.raceName} Grand Prix?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = uiState.selectedDriver ?: selectedDriver,
                onValueChange = { selectedDriver = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize =
                            coordinates.size.toSize()  // Makes the dropdown menu the same width as the text field
                    },
                label = { Text(stringResource(R.string.select_driver)) },
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "contentDescription",
                        modifier = Modifier.clickable { expanded = !expanded })
                },
                readOnly = true,
                enabled = uiState.predictionsEnabled
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                    .height(300.dp)
            ) {
                DriverRepo.drivers.sortedBy { driver -> driver.points }.reversed()
                    .forEach { driver ->
                        DropdownMenuItem(onClick = {
                            selectedDriver = driver.name
                            expanded = false
                        }) {
                            Text(text = driver.name)
                        }
                    }
            }
        }

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
                label = { Text(stringResource(R.string.enter_points)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.predictionsEnabled
            )
        }

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
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.predictionsEnabled
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm prediction?") },
            text = { Text("This cannot be undone!") },
            confirmButton = {
                Button(onClick = {
                    if (usedPoints != 0.0 &&
                        selectedDriver.isNotEmpty() &&
                        usedPoints <= uiState.availablePoints
                    ) {
                        viewModel.updateUsedPoints(usedPoints)
                        viewModel.updateAvailablePoints()
                        viewModel.updateSelectedDriver(selectedDriver)
                        viewModel.updatePredictionsEnabled(false)
                    }

                    onSubmitClicked()

                    showDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("Dismiss")
                }
            }
        )
    }
}