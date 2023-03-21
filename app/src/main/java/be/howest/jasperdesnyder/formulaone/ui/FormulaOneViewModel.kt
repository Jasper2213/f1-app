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
}

