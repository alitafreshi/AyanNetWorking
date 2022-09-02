package com.alitafreshi.ayan_networking.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*

class AppDataStoreImpl(private val dataStore: DataStore<Preferences>) : AppDataStore {

    override suspend fun <T> saveValue(key: String, value: T) {
        when (value) {
            is String -> dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = value
            }

            is Boolean -> dataStore.edit { preferences ->
                preferences[booleanPreferencesKey(key)] = value
            }

            is Int -> dataStore.edit { preferences ->
                preferences[intPreferencesKey(key)] = value

            }

            is Double -> dataStore.edit { preferences ->
                preferences[doublePreferencesKey(key)] = value
            }

            is Long -> dataStore.edit { preferences ->
                preferences[longPreferencesKey(key)] = value
            }
        }
    }
}