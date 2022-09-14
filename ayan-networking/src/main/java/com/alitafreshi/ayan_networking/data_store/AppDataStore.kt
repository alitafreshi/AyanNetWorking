package com.alitafreshi.ayan_networking.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.alitafreshi.ayan_networking.exceptions.DataStoreUnknownException
import kotlinx.coroutines.flow.first
import kotlin.jvm.Throws

interface AppDataStore {

    suspend fun <T> saveValue(
        key: String,
        value: T
    )
}

@Throws(Exception::class)
suspend inline fun <reified T> AppDataStore.readValue(
    key: String,
    defaultValue: T?,
    dataStore: DataStore<Preferences>
): T? = when (T::class) {
    String::class -> (dataStore.data.first()[stringPreferencesKey(key)] ?: defaultValue) as T
    Boolean::class -> (dataStore.data.first()[booleanPreferencesKey(key)] ?: defaultValue) as T
    Int::class -> (dataStore.data.first()[intPreferencesKey(key)] ?: defaultValue) as T
    Double::class -> (dataStore.data.first()[doublePreferencesKey(key)] ?: defaultValue) as T
    Long::class -> (dataStore.data.first()[longPreferencesKey(key)] ?: defaultValue) as T
    else -> throw DataStoreUnknownException("Unknown Data Type Please Use Primitive Data Types")
}