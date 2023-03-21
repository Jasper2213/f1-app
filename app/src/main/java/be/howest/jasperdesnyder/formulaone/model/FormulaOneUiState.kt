package be.howest.jasperdesnyder.formulaone.model

import be.howest.jasperdesnyder.formulaone.repositories.RaceRepo
import kotlin.random.Random

data class FormulaOneUiState (
    /* Values used for calendar/detail screen */
    var selectedRace: Race = RaceRepo.races.first(),

    /* Values used for predictions */
    var availablePoints: Double = 1200.00,
    var selectedDriver: String? = null,      // Only keep first name
    var usedPoints: Double = 0.00,
    var nextRace: Race = RaceRepo.races[Random.nextInt(RaceRepo.races.size)],
    var predictionsEnabled: Boolean = true
)