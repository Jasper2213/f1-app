package be.howest.jasperdesnyder.formulaone.model

import com.google.gson.annotations.SerializedName

data class ApiCalendarResponse(
    @SerializedName("MRData") var mrData : MRData? = MRData()
)

data class MRData(
    @SerializedName("RaceTable") var raceTable: RaceTable? = RaceTable(),
    @SerializedName("StandingsTable" ) var standingsTable : StandingsTable? = StandingsTable()
)

// Calendar data
data class RaceTable(
    @SerializedName("Races") var races: ArrayList<Race>  = ArrayList()
)

data class Race(
    @SerializedName("raceName"       ) var raceName       : String?       = null,
    @SerializedName("Circuit"        ) var circuit        : Circuit?      = Circuit(),
    @SerializedName("date"           ) var date           : String?       = null,
    @SerializedName("time"           ) var time           : String?       = null,
    @SerializedName("FirstPractice"  ) var firstPractice  : Session?      = Session(),
    @SerializedName("SecondPractice" ) var secondPractice : Session?      = Session(),
    @SerializedName("ThirdPractice"  ) var thirdPractice  : Session?      = Session(),
    @SerializedName("Qualifying"     ) var qualifying     : Session?      = Session(),
    @SerializedName("Sprint"         ) var sprint         : Session?      = null,
    @SerializedName("Results"        ) var results        : ArrayList<Results> = ArrayList()
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
    @SerializedName("Driver"       ) var driver       : Driver?      = Driver(),
    @SerializedName("Constructor"  ) var constructor  : Constructor? = Constructor()
)

data class Driver (
    @SerializedName("givenName"       ) var firstName       : String? = null,
    @SerializedName("familyName"      ) var lastName      : String? = null,
)

data class Constructor (
    @SerializedName("name"          ) var name          : String? = null,
)

// Driver standings data
data class StandingsTable (
    @SerializedName("StandingsLists" ) var standingsLists : ArrayList<StandingsLists> = arrayListOf()
)

data class StandingsLists (
    @SerializedName("DriverStandings" ) var driverStanding : ArrayList<DriverStanding> = arrayListOf()
)

data class DriverStanding (
    @SerializedName("position"     ) var position     : String?                 = null,
    @SerializedName("points"       ) var points       : String?                 = null,
    @SerializedName("Driver"       ) var driver       : Driver?                 = Driver(),
    @SerializedName("Constructors" ) var constructors : ArrayList<Constructor>  = arrayListOf()
)