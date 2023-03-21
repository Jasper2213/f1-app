package be.howest.jasperdesnyder.formulaone.ui

import androidx.lifecycle.ViewModel
import be.howest.jasperdesnyder.formulaone.model.FormulaOneUiState
import be.howest.jasperdesnyder.formulaone.model.Race
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FormulaOneViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormulaOneUiState())
    val uiState: StateFlow<FormulaOneUiState> = _uiState.asStateFlow()

    fun setSelectedRace(race: Race) {
        _uiState.value.selectedRace = race
    }

    fun getSelectedRace() = _uiState.value.selectedRace

    fun getAvailablePoints() = _uiState.value.availablePoints

    fun getSelectedDriver() = _uiState.value.selectedDriver

    fun setSelectedDriver(driver: String?) {
        _uiState.value.selectedDriver = driver
    }

    fun setUsedPoints(points: Int) {
        _uiState.value.usedPoints = points
        _uiState.value.availablePoints -= points
    }

    fun getUsedPoints() = _uiState.value.usedPoints

    fun getNextRace() = _uiState.value.nextRace
}

