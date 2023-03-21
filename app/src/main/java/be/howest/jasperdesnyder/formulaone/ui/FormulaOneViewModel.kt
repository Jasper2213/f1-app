package be.howest.jasperdesnyder.formulaone.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.howest.jasperdesnyder.formulaone.model.FormulaOneUiState
import be.howest.jasperdesnyder.formulaone.model.Race
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FormulaOneViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormulaOneUiState())
    val uiState: StateFlow<FormulaOneUiState> = _uiState.asStateFlow()

    private val _selectedRace = MutableLiveData(_uiState.value.selectedRace)
    var selectedRace: Race = _selectedRace.value!!

    private val _availablePoints = MutableLiveData(_uiState.value.availablePoints)
    val availablePoints: MutableLiveData<Double> = _availablePoints

    private val _selectedDriver = MutableLiveData(_uiState.value.selectedDriver)
    val selectedDriver: MutableLiveData<String?> = _selectedDriver

    private val _usedPoints = MutableLiveData(_uiState.value.usedPoints)
    val usedPoints: MutableLiveData<Double> = _usedPoints

    private val _nextRace = MutableLiveData(_uiState.value.nextRace)
    val nexRace: MutableLiveData<Race> = _nextRace

    private val _predictionsEnabled = MutableLiveData(_uiState.value.predictionsEnabled)
    val predictionsEnabled: MutableLiveData<Boolean> = _predictionsEnabled

//    fun setSelectedRace(race: Race) {
//        _uiState.value.selectedRace = race
//    }
//
//    fun getSelectedRace() = _uiState.value.selectedRace
//
//    fun getAvailablePoints() = _uiState.value.availablePoints
//
//    fun getSelectedDriver() = _uiState.value.selectedDriver
//
//    fun setSelectedDriver(driver: String?) {
//        _uiState.value.selectedDriver = driver
//    }
//
//    fun setUsedPoints(points: Double) {
////        var pointsToUse = points
////
////        if (points < 0) pointsToUse *= -1
//
//        _uiState.value.usedPoints = points
////        _uiState.value.availablePoints -= points
//    }
//
//    fun setAvailablePoints(points: Double) {
//        _uiState.value.availablePoints -= points
//    }
//
//    fun getUsedPoints() = _uiState.value.usedPoints
//
//    fun getNextRace() = _uiState.value.nextRace
//
//    fun disablePredictions() {
//        _uiState.value.predictionsEnabled = false
//    }
//    fun getPredictionsEnabled() = _uiState.value.predictionsEnabled
}

