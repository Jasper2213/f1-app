package be.howest.jasperdesnyder.formulaone.model

import be.howest.jasperdesnyder.formulaone.repositories.RaceRepo
import kotlin.random.Random

data class FormulaOneUiState (
    /* Values used for calendar/detail screen */
    var selectedRace: Race = RaceRepo.races.first(),

    /* Values used for predictions */
    var availablePoints: Int = 1200,
    var selectedDriver: String? = null,      // Only keep first name
    var usedPoints: Int = 0,
    var nextRace: Race = RaceRepo.races[Random.nextInt(RaceRepo.races.size)]
)