package com.alitafreshi.ayan_networking.interactors.header_manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.alitafreshi.ayan_networking.constants.Constants.PERSIAN_APP_LANGUAGE_KEY
import com.alitafreshi.ayan_networking.constants.Constants.REMOTE_LANGUAGE_HEADER_KEY
import com.alitafreshi.ayan_networking.constants.Constants.SELECTED_APP_LANGUAGE_KEY
import com.alitafreshi.ayan_networking.data_store.AppDataStore
import com.alitafreshi.ayan_networking.data_store.readValue

class AyanHeaderManager(
    private val appDataState: AppDataStore,
    private val dataStore: DataStore<Preferences>
) {
    suspend operator fun invoke(requestHeaders: HashMap<String, String>? = null): HashMap<String, String> =
        requestHeaders?.apply {
            requestHeaders.forEach { (key, value) ->
                if (key != REMOTE_LANGUAGE_HEADER_KEY) {
                    requestHeaders[REMOTE_LANGUAGE_HEADER_KEY] = appDataState.readValue(
                        key = SELECTED_APP_LANGUAGE_KEY,
                        defaultValue = PERSIAN_APP_LANGUAGE_KEY,
                        dataStore = dataStore
                    )
                }
            }
        } ?: hashMapOf(
            REMOTE_LANGUAGE_HEADER_KEY to appDataState.readValue(
                key = SELECTED_APP_LANGUAGE_KEY,
                defaultValue = PERSIAN_APP_LANGUAGE_KEY,
                dataStore = dataStore
            )
        )
}
