package be.howest.jasperdesnyder.formulaone.model

import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("circuitId"   ) var circuitId   : String?   = null,
    @SerializedName("url"         ) var url         : String?   = null,
    @SerializedName("circuitName" ) var circuitName : String?   = null,
    @SerializedName("Location"    ) var Location    : Location? = Location()
)