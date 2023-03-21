package be.howest.jasperdesnyder.formulaone.model

data class Race (
    val title: Int,
    val date: String,
    val trackLayoutRes: Int,
    var results: List<Result> = emptyList(),
    val sessions: List<Session> = emptyList()
)