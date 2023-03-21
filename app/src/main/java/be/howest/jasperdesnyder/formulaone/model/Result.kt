package be.howest.jasperdesnyder.formulaone.model

data class Result (
    val driver: String,
    val team: String,
    val position: Int,
    val points: Int = 0
)