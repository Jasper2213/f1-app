package formulaone.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
){
    private companion object {
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")

        val PREDICTIONS_ENABLED = booleanPreferencesKey("predictions_enabled")
        val AVAILABLE_POINTS = doublePreferencesKey("available_points")
        val USED_POINTS = doublePreferencesKey("used_points")
        val PREDICTED_DRIVER = stringPreferencesKey("predicted_driver")
        val RACE_PREDICTED_ON = stringPreferencesKey("race_predicted_on")
    }

    val notificationsEnabled: Flow<Boolean> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
        preferences[NOTIFICATIONS_ENABLED] ?: false
    }

    val predictionsEnabled: Flow<Boolean> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[PREDICTIONS_ENABLED] ?: true
        }

    val availablePoints: Flow<Double> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[AVAILABLE_POINTS] ?: 2000.00
        }

    val usedPoints: Flow<Double> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[USED_POINTS] ?: 0.00
        }

    val predictedDriver: Flow<String?> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[PREDICTED_DRIVER]
        }

    val racePredictedOn: Flow<String?> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[RACE_PREDICTED_ON]
        }

    suspend fun saveNotificationsPreference(notificationsEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = notificationsEnabled
        }
    }

    suspend fun savePredictionsPreference(predictionsEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREDICTIONS_ENABLED] = predictionsEnabled
        }
    }

    suspend fun getPredictionsEnabled(): Boolean {
        return dataStore.data
            .catch {
                if(it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { preferences ->
                preferences[PREDICTIONS_ENABLED] ?: false
            }.first()
    }

    suspend fun saveAvailablePoints(availablePoints: Double) {
        dataStore.edit { preferences ->
            preferences[AVAILABLE_POINTS] = availablePoints
        }
    }

    suspend fun getAvailablePoints(): Double {
        return dataStore.data
            .catch {
                if(it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { preferences ->
                preferences[AVAILABLE_POINTS] ?: 2000.00
            }.first()
    }

    suspend fun saveUsedPoints(usedPoints: Double) {
        dataStore.edit { preferences ->
            preferences[USED_POINTS] = usedPoints
        }
    }

    suspend fun getUsedPoints(): Double {
        return dataStore.data
            .catch {
                if(it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { preferences ->
                preferences[USED_POINTS] ?: 0.00
            }.first()
    }

    suspend fun savePredictedDriver(predictedDriver: String) {
        dataStore.edit { preferences ->
            preferences[PREDICTED_DRIVER] = predictedDriver
        }
    }

    suspend fun getPredictedDriver(): String? {
        return dataStore.data
            .catch {
                if(it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { preferences ->
                preferences[PREDICTED_DRIVER]
            }.first()
    }

    suspend fun saveRacePredictedOn(racePredictedOn: String) {
        dataStore.edit { preferences ->
            preferences[RACE_PREDICTED_ON] = racePredictedOn
        }
    }

    suspend fun getRacePredictedOn(): String? {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { preferences ->
                preferences[RACE_PREDICTED_ON]
            }.first()
    }
}