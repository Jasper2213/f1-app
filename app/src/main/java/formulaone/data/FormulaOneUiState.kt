package formulaone.data

import formulaone.model.Race

data class FormulaOneUiState (
    /* Values used for calendar/detail screen */
    var selectedRace: Race? = null,

    /* Values used for predictions */
    var availablePoints: Double = 1200.00,
    var selectedDriver: String? = null,      // Only need to keep first name for this to function
    var usedPoints: Double = 0.00,
    var nextRace: Race? = null,
    var previousRace: Race? = null,
    var predictionsEnabled: Boolean = true,
    var racePredictedOn: String? = null,

    var notificationsEnabled: Boolean = false,
)