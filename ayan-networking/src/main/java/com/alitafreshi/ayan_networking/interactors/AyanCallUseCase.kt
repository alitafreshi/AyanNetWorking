package com.alitafreshi.ayan_networking.interactors

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.alitafreshi.ayan_networking.AyanRepository
import com.alitafreshi.ayan_networking.Identity
import com.alitafreshi.ayan_networking.constants.ApiErrorCode
import com.alitafreshi.ayan_networking.constants.Constants
import com.alitafreshi.ayan_networking.constants.Constants.USER_SAVE_TOKEN_KEY
import com.alitafreshi.ayan_networking.constants.exceptions.AyanException
import com.alitafreshi.ayan_networking.constants.exceptions.LoginRequiredException
import com.alitafreshi.ayan_networking.data_store.AppDataStore
import com.alitafreshi.ayan_networking.data_store.readValue
import com.alitafreshi.ayan_networking.interactors.header_manager.AyanHeaderManager
import com.alitafreshi.ayan_networking.interactors.local_message_manager.LocalMessageHandlerUseCase
import com.alitafreshi.ayan_networking.state_handling.DataState
import com.alitafreshi.ayan_networking.state_handling.UIComponent
import com.alitafreshi.ayan_networking.state_handling.UIComponentState
import com.alitafreshi.ayan_networking.state_strategy.StateHandlingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*

class AyanCallUseCase(
    private val ayanRepository: AyanRepository,
    private val appDataState: AppDataStore,
    private val dataStore: DataStore<Preferences>,
    private val ayanHeaderManager: AyanHeaderManager,
    private val localMessageHandlerUseCas: LocalMessageHandlerUseCase,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun <Input, Output, Event> invoke(
        baseUrl: String? = null,
        hasIdentity: Boolean = true,
        endpoint: String,
        input: Input,
        stateEvent: Event,
        requestHeaders: HashMap<String, String>? = null
    ): Flow<DataState<Output, Event>> = flow<DataState<Output, Event>> {


        val result = ayanRepository.ayanCall<Input, Output>(
            baseUrl = baseUrl,
            endpoint = endpoint,
            input = input,
            identity = if (hasIdentity) appDataState.readValue<Identity>(
                key = USER_SAVE_TOKEN_KEY,
                dataStore = dataStore
            ) else null,
            requestHeaders = ayanHeaderManager(requestHeaders = requestHeaders)
        )

        when (result.status.code) {
            ApiErrorCode.LOGIN_REQUIRED -> {
                throw LoginRequiredException(
                    message = result.status.description,
                    causeCoroutineName = currentCoroutineContext()[CoroutineName]?.name
                )
            }
        }

        emit(DataState.Data(data = result.parameters, stateEvent = stateEvent))

    }.onStart {
        emit(
            DataState.Loading(
                loadingUiComponentState = UIComponentState.Loading,
                stateEvent = stateEvent
            )
        )
    }.catch { throwable ->
        //TODO HERE WE SHOULD PASS THE ERROR CODES TO THE CLIENT
        val appLanguage = appDataState.readValue(
            key = Constants.SELECTED_APP_LANGUAGE_KEY,
            defaultValue = Constants.PERSIAN_APP_LANGUAGE_KEY,
            dataStore = dataStore
        )

        emit(
            DataState.Error(
                throwable = throwable,
                uiComponent = UIComponent.Error(
                    errorDescription = localMessageHandlerUseCas(
                        appLanguage = appLanguage,
                        throwable = throwable
                    )
                ),
                stateEvent = stateEvent
            )
        )
    }.onCompletion {
        emit(DataState.Loading(loadingUiComponentState = UIComponentState.Idle, stateEvent = stateEvent))
    }.flowOn(ioDispatcher)
}


sealed class LocalStateEvent {
    object GetData : LocalStateEvent()
}

class ViewModel(
    statsQueue: StateHandlingUseCase<UIComponent>,
    ayanCallUseCase: AyanCallUseCase,
    viewModelScope: CoroutineScope
) {
    init {
        ayanCallUseCase<String, String, LocalStateEvent>(
            endpoint = "",
            input = "",
            stateEvent = LocalStateEvent.GetData
        ).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    println("The Request State = ${dataState.loadingUiComponentState} and the State Event is ${dataState.stateEvent}")
                }

                is DataState.Data -> {
                    dataState.stateEvent
                }

                is DataState.Error -> {
                    dataState.stateEvent
                    if (dataState.)
                }
            }

        }.launchIn(viewModelScope)
    }
}