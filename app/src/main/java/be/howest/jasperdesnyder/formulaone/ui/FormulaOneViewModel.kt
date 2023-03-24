package be.howest.jasperdesnyder.formulaone.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jasperdesnyder.formulaone.model.FormulaOneUiState
import be.howest.jasperdesnyder.formulaone.model.Race
import be.howest.jasperdesnyder.formulaone.network.FormulaOneApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.Normalizer.Form

sealed interface FormulaOneApiUiState {
    data class Success(val races: List<Race>, val nextRace: Race) : FormulaOneApiUiState
    object Error : FormulaOneApiUiState
    object Loading : FormulaOneApiUiState
}

class FormulaOneViewModel : ViewModel() {

    var formulaOneApiUiState: FormulaOneApiUiState by mutableStateOf(FormulaOneApiUiState.Loading)
        private set

    init {
        getSeasonInformation()
    }

    private fun getSeasonInformation() {
        viewModelScope.launch {
            formulaOneApiUiState =
                try {
                    var apiResponse = FormulaOneApi.retrofitService.getCalendar()
                    val races = apiResponse.MRData?.RaceTable?.Races

                    apiResponse = FormulaOneApi.retrofitService.getNextRace()
                    val nextRace = apiResponse.MRData?.RaceTable?.Races?.get(0)

                    FormulaOneApiUiState.Success(
                        races = races!!,
                        nextRace = nextRace!!
                    )
                } catch (e: IOException) {
                    FormulaOneApiUiState.Error
                } catch (e: HttpException) {
                    FormulaOneApiUiState.Error
                }
        }
    }

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

