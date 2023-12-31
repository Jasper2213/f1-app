package formulaone.network

import formulaone.model.ApiCalendarResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://ergast.com/api/f1/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface FormulaOneApiService {
    @GET("current.json")
    suspend fun getCalendar(): ApiCalendarResponse

    @GET("current/next.json")
    suspend fun getNextRace(): ApiCalendarResponse

    @GET("current/last/results.json")
    suspend fun getPreviousRace(): ApiCalendarResponse

    @GET("current/results.json")
    suspend fun getResults(): ApiCalendarResponse

    @GET("current/driverStandings.json")
    suspend fun getDriversStandings(): ApiCalendarResponse

    @GET("current/constructorStandings.json")
    suspend fun getConstructorsStandings(): ApiCalendarResponse
}

object FormulaOneApi {
    val retrofitService: FormulaOneApiService by lazy {
        retrofit.create(FormulaOneApiService::class.java)
    }
}