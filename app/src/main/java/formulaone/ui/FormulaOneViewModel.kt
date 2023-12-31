package formulaone.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import formulaone.FormulaOneApplication
import formulaone.data.FormulaOneUiState
import formulaone.model.MRData
import formulaone.model.Race
import formulaone.network.FormulaOneApi
import formulaone.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface FormulaOneApiUiState {
    data class Success(val formulaOneData: MRData, val nextRace: Race) : FormulaOneApiUiState
    object Error : FormulaOneApiUiState
    object Loading : FormulaOneApiUiState
}

class FormulaOneViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val uiState: StateFlow<FormulaOneUiState> =
        userPreferencesRepository.notificationsEnabled.map { notificationsEnabled ->
            FormulaOneUiState(
                availablePoints = userPreferencesRepository.getAvailablePoints(),
                usedPoints = userPreferencesRepository.getUsedPoints(),
                predictionsEnabled = userPreferencesRepository.getPredictionsEnabled(),
                selectedDriver = userPreferencesRepository.getPredictedDriver(),
                racePredictedOn = userPreferencesRepository.getRacePredictedOn(),
                notificationsEnabled = notificationsEnabled
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FormulaOneUiState()
            )

    fun updateNotificationsEnabled(notificationsEnabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveNotificationsPreference(notificationsEnabled)
        }
    }

    fun updatePredictionsEnabled(predictionsEnabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.savePredictionsPreference(predictionsEnabled)
        }
    }

    fun updateAvailablePoints() {
        viewModelScope.launch {
            val availablePoints: Double = userPreferencesRepository.getAvailablePoints()
            val usedPoints: Double = userPreferencesRepository.getUsedPoints()
            userPreferencesRepository.saveAvailablePoints(availablePoints - usedPoints)
        }
    }

    fun updateUsedPoints(usedPoints: Double) {
        viewModelScope.launch {
            userPreferencesRepository.saveUsedPoints(usedPoints)
        }
    }

    fun updatePredictedDriver(predictedDriver: String) {
        viewModelScope.launch {
            userPreferencesRepository.savePredictedDriver(predictedDriver)
        }
    }

    fun updateRacePredictedOn(racePredictedOn: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveRacePredictedOn(racePredictedOn)
        }
    }

    var formulaOneApiUiState: FormulaOneApiUiState by mutableStateOf(FormulaOneApiUiState.Loading)

    fun getSeasonInformation() {
        viewModelScope.launch {
            formulaOneApiUiState =
                try {
                    val mrData = getMrData()

                    val nextRace = getNextRace()
                    uiState.value.nextRace = nextRace

                    val previousRace = getPreviousRace()
                    uiState.value.previousRace = previousRace

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

    fun updateSelectedRace(race: Race) {
        uiState.value.selectedRace = race
    }

    fun setAvailablePoints(d: Double) {
        viewModelScope.launch {
            userPreferencesRepository.saveAvailablePoints(d)
        }
    }

//    private fun updateUiState() {
//        uiState.update { currentState ->
//            currentState.copy(
////                availablePoints = currentState.availablePoints - currentState.usedPoints,
//                selectedDriver = currentState.selectedDriver,
//                usedPoints = currentState.usedPoints,
//                nextRace = currentState.nextRace,
//                predictionsEnabled = currentState.predictionsEnabled
//            )
//        }
//    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FormulaOneApplication)
                FormulaOneViewModel(application.userPreferencesRepository)
            }
        }
    }
}

private suspend fun getNextRace(): Race {
    val apiResponse = FormulaOneApi.retrofitService.getNextRace()
    val nextRace = apiResponse.mrData?.raceTable?.races?.get(0)

    return nextRace!!
}

private suspend fun getPreviousRace(): Race {
    val apiResponse = FormulaOneApi.retrofitService.getPreviousRace()
    val previousRace = apiResponse.mrData?.raceTable?.races?.get(0)

    return previousRace!!
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