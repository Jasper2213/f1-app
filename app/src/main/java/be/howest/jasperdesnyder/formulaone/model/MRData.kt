package be.howest.jasperdesnyder.formulaone.model

import com.google.gson.annotations.SerializedName

data class MRData(

    @SerializedName("xmlns"     ) var xmlns     : String?    = null,
    @SerializedName("series"    ) var series    : String?    = null,
    @SerializedName("url"       ) var url       : String?    = null,
    @SerializedName("limit"     ) var limit     : String?    = null,
    @SerializedName("offset"    ) var offset    : String?    = null,
    @SerializedName("total"     ) var total     : String?    = null,
    @SerializedName("RaceTable" ) var RaceTable : RaceTable? = RaceTable()
)

