package be.howest.jasperdesnyder.formulaone.model

data class Session(
    val title: String,
    val day: String,
    val startTime: String,
    val endTime: String? = null
)