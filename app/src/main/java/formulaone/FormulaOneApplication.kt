package formulaone

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import formulaone.repositories.UserPreferencesRepository

private const val NOTIFICATION_PREFERENCES = "notification_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = NOTIFICATION_PREFERENCES
)

class FormulaOneApplication: Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}