package be.howest.jasperdesnyder.formulaone.model

import com.google.gson.annotations.SerializedName

data class Session(
    @SerializedName("date" ) var date : String? = null,
    @SerializedName("time" ) var time : String? = null
)