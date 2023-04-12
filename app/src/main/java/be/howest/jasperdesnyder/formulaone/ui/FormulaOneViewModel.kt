package be.howest.jasperdesnyder.formulaone.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jasperdesnyder.formulaone.model.FormulaOneUiState
import be.howest.jasperdesnyder.formulaone.model.MRData
import be.howest.jasperdesnyder.formulaone.model.Race
import be.howest.jasperdesnyder.formulaone.network.FormulaOneApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface FormulaOneApiUiState {
    data class Success(val formulaOneData: MRData, val nextRace: Race) : FormulaOneApiUiState
    object Error : FormulaOneApiUiState
    object Loading : FormulaOneApiUiState
}

class FormulaOneViewModel : ViewModel() {

    var formulaOneApiUiState: FormulaOneApiUiState by mutableStateOf(FormulaOneApiUiState.Loading)

    fun getSeasonInformation() {
        viewModelScope.launch {
            formulaOneApiUiState =
                try {
                    val mrData = getMrData()

                    val nextRace = getNextRace()
                    _uiState.value.nextRace = nextRace

                    addResultsToMrData(mrData)
                    addDriversStandingsToMrData(mrData)
                    addConstructorsStandingsToMrData(mrData)

                    FormulaOneApiUiState.Success(
                        formulaOneData = mrData,
                        nextRace = nextRace
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

    fun updateNotificationsEnabled(enabled: Boolean) {
        _uiState.value.notificationsEnabled = enabled
    }
}

private suspend fun getNextRace(): Race {
    val apiResponse = FormulaOneApi.retrofitService.getNextRace()
    val nextRace = apiResponse.mrData?.raceTable?.races?.get(0)

    return nextRace!!
}

private suspend fun getMrData(): MRData {
    val apiResponse = FormulaOneApi.retrofitService.getCalendar()
    val mrData = apiResponse.mrData

    return mrData!!
}

private suspend fun addResultsToMrData(mrData: MRData) {
    val apiResponse = FormulaOneApi.retrofitService.getResults()
    val results = apiResponse.mrData?.raceTable?.races

    for (race in mrData.raceTable?.races!!)
        for (result in results!!)
            if (race.raceName == result.raceName)
                race.results = result.results
}

private suspend fun addDriversStandingsToMrData(mrData: MRData) {
    val apiResponse = FormulaOneApi.retrofitService.getDriversStandings()
    val driversStandings = apiResponse.mrData?.standingsTable?.standingsLists!!

    mrData.standingsTable?.standingsLists = driversStandings
}

private suspend fun addConstructorsStandingsToMrData(mrData: MRData) {
    val apiResponse = FormulaOneApi.retrofitService.getConstructorsStandings()
    val constructorsStandings = apiResponse.mrData?.standingsTable?.standingsLists!!

    mrData.standingsTable?.standingsLists!![0].constructorStandings = constructorsStandings[0].constructorStandings
}