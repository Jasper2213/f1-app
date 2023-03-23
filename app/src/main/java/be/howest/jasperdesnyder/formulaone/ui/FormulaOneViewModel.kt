package be.howest.jasperdesnyder.formulaone.ui

import androidx.lifecycle.ViewModel
import be.howest.jasperdesnyder.formulaone.model.FormulaOneUiState
import be.howest.jasperdesnyder.formulaone.model.Race
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormulaOneViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormulaOneUiState())
    val uiState: StateFlow<FormulaOneUiState> = _uiState.asStateFlow()

    fun updateAvailablePoints() {
        _uiState.value.availablePoints -= _uiState.value.usedPoints
    }

    fun updateSelectedDriver(driver: String?) {
        _uiState.value.selectedDriver = driver
    }

    fun updateUsedPoints(points: Double) {
        _uiState.value.usedPoints = points
    }

    fun updatePredictionsEnabled(enabled: Boolean) {
        _uiState.value.predictionsEnabled = enabled
    }

    fun updateSelectedRace(race: Race) {
        _uiState.value.selectedRace = race
    }

//    private fun updateUiState() {
//        _uiState.update { currentState ->
//            currentState.copy(
////                availablePoints = currentState.availablePoints - currentState.usedPoints,
//                selectedDriver = currentState.selectedDriver,
//                usedPoints = currentState.usedPoints,
//                nextRace = currentState.nextRace,
//                predictionsEnabled = currentState.predictionsEnabled
//            )
//        }
//    }
}

