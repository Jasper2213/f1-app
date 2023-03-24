package be.howest.jasperdesnyder.formulaone.model

import com.google.gson.annotations.SerializedName

data class ApiCalendarResponse(
    @SerializedName("MRData" ) var MRData : MRData? = MRData()
)
