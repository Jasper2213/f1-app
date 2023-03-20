package be.howest.jasperdesnyder.formulaone.model

import be.howest.jasperdesnyder.formulaone.repositories.RaceRepo

data class FormulaOneUiState (
    var selectedRace: Race = RaceRepo.races.first()
)