package be.howest.jasperdesnyder.formulaone.model

import com.google.gson.annotations.SerializedName

data class ApiCalendarResponse(
    @SerializedName("MRData") var MRData : MRData? = MRData()
)

data class MRData(
    @SerializedName("RaceTable") var RaceTable: RaceTable? = RaceTable(),
    @SerializedName("DriverTable" ) var DriverTable : DriverTable? = DriverTable()
)

// Calendar data
data class RaceTable(
    @SerializedName("Races") var Races: ArrayList<Race>  = ArrayList()
)

data class Race(
    @SerializedName("raceName"       ) var raceName       : String?       = null,
    @SerializedName("Circuit"        ) var Circuit        : Circuit?      = Circuit(),
    @SerializedName("date"           ) var date           : String?       = null,
    @SerializedName("time"           ) var time           : String?       = null,
    @SerializedName("FirstPractice"  ) var FirstPractice  : Session?      = Session(),
    @SerializedName("SecondPractice" ) var SecondPractice : Session?      = Session(),
    @SerializedName("ThirdPractice"  ) var ThirdPractice  : Session?      = Session(),
    @SerializedName("Qualifying"     ) var Qualifying     : Session?      = Session(),
    @SerializedName("Sprint"         ) var sprint         : Session?      = null,
    @SerializedName("Results"        ) var Results        : ArrayList<Results> = ArrayList()
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

// Results data
data class Results (
    @SerializedName("position"     ) var position     : String?      = null,
    @SerializedName("points"       ) var points       : String?      = null,
    @SerializedName("Driver"       ) var Driver       : Driver?      = Driver(),
    @SerializedName("Constructor"  ) var Constructor  : Constructor? = Constructor()
)

data class Driver (
    @SerializedName("givenName"       ) var givenName       : String? = null,
    @SerializedName("familyName"      ) var familyName      : String? = null,
)

data class Constructor (
    @SerializedName("name"          ) var name          : String? = null,
)

// Drivers data
data class DriverTable (
    @SerializedName("Drivers" ) var Drivers : ArrayList<Drivers> = ArrayList()
)

data class Drivers (
    @SerializedName("givenName"       ) var givenName       : String? = null,
    @SerializedName("familyName"      ) var familyName      : String? = null,
)