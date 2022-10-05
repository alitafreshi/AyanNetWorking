package com.alitafreshi.ayan_networking.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.alitafreshi.ayan_networking.Identity
import com.alitafreshi.ayan_networking.constants.exceptions.AyanLocalException
import kotlinx.coroutines.flow.first

interface AppDataStore {

    suspend fun <T> saveValue(
        key: String,
        value: T
    )
}

@Throws(Exception::class)
suspend inline fun <reified T> AppDataStore.readValue(
    key: String,
    defaultValue: T? = null,
    dataStore: DataStore<Preferences>
): T = when (T::class) {
    String::class -> (dataStore.data.first()[stringPreferencesKey(key)] ?: defaultValue) as T
    Boolean::class -> (dataStore.data.first()[booleanPreferencesKey(key)] ?: defaultValue) as T
    Int::class -> (dataStore.data.first()[intPreferencesKey(key)] ?: defaultValue) as T
    Double::class -> (dataStore.data.first()[doublePreferencesKey(key)] ?: defaultValue) as T
    Long::class -> (dataStore.data.first()[longPreferencesKey(key)] ?: defaultValue) as T
    Identity::class -> (Identity(token = dataStore.data.first()[stringPreferencesKey(key)])) as T
    else -> throw AyanLocalException.DataStoreUnknownException("Unknown Data Type Please Use Primitive Data Types")
}