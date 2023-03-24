package be.howest.jasperdesnyder.formulaone.model

import com.google.gson.annotations.SerializedName

data class ApiCalendarResponse(
    @SerializedName("MRData") var MRData : MRData? = MRData()
)

data class MRData(
    @SerializedName("RaceTable") var RaceTable: RaceTable? = RaceTable()
)

data class RaceTable(
    @SerializedName("Races") var Races: List<Race>  = emptyList()
)

data class Race(
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

data class Circuit(
    @SerializedName("circuitId"   ) var circuitId   : String?   = null,
    @SerializedName("circuitName" ) var circuitName : String?   = null,
    @SerializedName("Location"    ) var Location    : Location? = Location()
)

data class Location(
    @SerializedName("lat"      ) var lat      : String? = null,
    @SerializedName("long"     ) var long     : String? = null,
    @SerializedName("locality" ) var locality : String? = null,
    @SerializedName("country"  ) var country  : String? = null
)

data class Session(
    @SerializedName("date" ) var date : String? = null,
    @SerializedName("time" ) var time : String? = null
)