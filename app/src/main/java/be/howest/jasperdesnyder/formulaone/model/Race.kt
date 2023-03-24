package be.howest.jasperdesnyder.formulaone.model

import com.google.gson.annotations.SerializedName

data class Race(
    @SerializedName("season"         ) var season         : String?     = null,
    @SerializedName("round"          ) var round          : String?     = null,
    @SerializedName("url"            ) var url            : String?     = null,
    @SerializedName("raceName"       ) var raceName       : String?     = null,
    @SerializedName("Circuit"        ) var Circuit        : Circuit?    = Circuit(),
    @SerializedName("date"           ) var date           : String?     = null,
    @SerializedName("time"           ) var time           : String?     = null,
    @SerializedName("FirstPractice"  ) var FirstPractice  : Session?    = Session(),
    @SerializedName("SecondPractice" ) var SecondPractice : Session?    = Session(),
    @SerializedName("ThirdPractice"  ) var ThirdPractice  : Session?    = Session(),
    @SerializedName("Qualifying"     ) var Qualifying     : Session?    = Session(),
    @SerializedName("Sprint"         ) var sprint         : Session?    = null
)