package be.howest.jasperdesnyder.formulaone.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import be.howest.jasperdesnyder.formulaone.repositories.DriverRepo
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneViewModel

@Composable
fun PredictionScreen(
    viewModel: FormulaOneViewModel,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDriver by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = selectedDriver,
            onValueChange = { selectedDriver = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()  // Makes the dropdown menu the same width as the text field
                },
            label = { Text("Select driver") },
            trailingIcon = {
                Icon(
                    icon,
                    "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .height(300.dp)
        ) {
            DriverRepo.drivers.sortedBy { driver -> driver.points }.reversed().forEach { driver ->
                DropdownMenuItem(onClick = {
                    selectedDriver = driver.name
                    expanded = false
                }) {
                    Text(text = driver.name)
                }
            }
        }
    }
}