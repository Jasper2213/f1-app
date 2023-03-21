package be.howest.jasperdesnyder.formulaone.ui.screens

import android.util.Log
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
import be.howest.jasperdesnyder.formulaone.repositories.DriverRepo
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneViewModel

@Composable
fun PredictionScreen(
    onSubmitClicked: () -> Unit,
    viewModel: FormulaOneViewModel,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDriver by rememberSaveable { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    var usedPoints by rememberSaveable { mutableStateOf(0.0) }

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
                text = "Your current amount of points:",
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
            Text(
                text = viewModel.availablePoints.value.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Column {
            Text(
                text = "Who will win the ${stringResource(viewModel.nexRace.value!!.title)} Grand Prix?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = viewModel.selectedDriver.value ?: selectedDriver,
                onValueChange = { selectedDriver = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize =
                            coordinates.size.toSize()  // Makes the dropdown menu the same width as the text field
                    },
                label = { Text("Select driver") },
                trailingIcon = {
                    Icon(
                        icon,
                        "contentDescription",
                        Modifier.clickable { expanded = !expanded })
                },
                readOnly = true,
                enabled = viewModel.predictionsEnabled.value!!
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
                text = "How many points do you want to use?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = if (!viewModel.predictionsEnabled.value!!) viewModel.usedPoints.value!!.toString()
                            else if (usedPoints == 0.0) ""
                            else usedPoints.toString(),
                onValueChange = {
                    usedPoints =
                        if (it.isNotEmpty()) {
                            it.toDoubleOrNull() ?: usedPoints
                        } else {
                            0.0
                        }
                    viewModel.usedPoints.value = usedPoints
                },
                label = { Text("Enter points") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.predictionsEnabled.value!!
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Potential points to win (x1.2):",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = (viewModel.usedPoints.value!! * 1.2).toString(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = {
                // TODO: Check if everything is filled in
                // TODO: Check if used points is not higher than available points

                viewModel.usedPoints.value = usedPoints
                viewModel.availablePoints.value = viewModel.availablePoints.value!! - usedPoints
                viewModel.selectedDriver.value = selectedDriver
                viewModel.predictionsEnabled.value = false

                Log.d("PredictionScreen", viewModel.selectedDriver.toString())
                Log.d("PredictionScreen", viewModel.usedPoints.toString())
                Log.d("PredictionScreen", viewModel.predictionsEnabled.toString())

                onSubmitClicked()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.predictionsEnabled.value!!
        ) {
            Text(text = "Submit prediction")
        }
    }
}