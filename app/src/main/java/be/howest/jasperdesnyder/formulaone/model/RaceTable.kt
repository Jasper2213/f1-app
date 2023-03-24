package be.howest.jasperdesnyder.formulaone.model

import com.google.gson.annotations.SerializedName

data class RaceTable(
    @SerializedName("season" ) var season : String?     = null,
    @SerializedName("Races"  ) var Races  : List<Race>  = emptyList()

)