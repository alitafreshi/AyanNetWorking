package com.alitafreshi.ayan_networking.interactors

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.alitafreshi.ayan_networking.AyanRepository
import com.alitafreshi.ayan_networking.Identity
import com.alitafreshi.ayan_networking.constants.ApiErrorCode
import com.alitafreshi.ayan_networking.constants.ApiSuccessCode
import com.alitafreshi.ayan_networking.data_store.AppDataStore
import com.alitafreshi.ayan_networking.data_store.readValue
import com.alitafreshi.ayan_networking.constants.exceptions.LoginRequiredException
import com.alitafreshi.ayan_networking.constants.exceptions.ServerErrorException
import com.alitafreshi.ayan_networking.constants.exceptions.SuccessCompletionException
import com.alitafreshi.ayan_networking.interactors.header_manager.AyanHeaderManager
import com.alitafreshi.ayan_networking.state_handling.*
import com.alitafreshi.ayan_networking.state_strategy.StateHandlingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.job

class AyanSimpleCallUseCase(
    private val ayanRepository: AyanRepository,
    private val appDataState: AppDataStore,
    private val dataStore: DataStore<Preferences>,
    private val ayanHeaderManager: AyanHeaderManager,
    private val ioDispatcher: CoroutineDispatcher,
    private val statsQueue: StateHandlingUseCase<UIComponent>
) {
    operator fun <Input, Output, Event> invoke(
        hasIdentity: Boolean = true,
        endpoint: String,
        input: Input,
        stateEvent: Event,
        requestHeaders: HashMap<String, String>? = null
    ): Flow<Output?> = flow {

        val result = ayanRepository.simpleCall<Input, Output>(
            endpoint = endpoint,
            input = input,
            identity = if (hasIdentity) appDataState.readValue<Identity>(
                //TODO It Should Be Converted To the Constance
                key = "ayan_token",
                defaultValue = null,
                dataStore = dataStore
            ) else null,
            requestHeaders = ayanHeaderManager(requestHeaders = requestHeaders)
        )

        if (result.status.code != ApiSuccessCode.Success)
            when (result.status.code) {
                ApiErrorCode.LOGIN_REQUIRED -> {
                    throw LoginRequiredException(
                        message = result.status.description,
                        causeCoroutineName = currentCoroutineContext()[CoroutineName.Key]?.name
                    )
                }
                else -> {
                    throw ServerErrorException(
                        errorCode = result.status.code,
                        message = result.status.description,
                        causeCoroutineName = currentCoroutineContext()[CoroutineName.Key]?.name
                    )
                }
            }

        emit(result.parameters)

    }.onStart {
        statsQueue.addOrUpdate(
            RequestGenericState(
                uiComponent = UIComponent.Loading(),
                stateEvent = stateEvent,
                job = currentCoroutineContext().job,
                requestState = RequestState.Loading
            )
        )

    }.catch { throwable ->
        //TODO We For Positions That Servers Error Message Dose Not Has Any Error Message We Should Return A Default Message
        statsQueue.handleCancellation(
            throwable = throwable,
            uiComponent = UIComponent.Error(
                errorDescription = UiText.DynamicString(
                    value = throwable.message ?: ""
                )
            ),
            stateEvent = stateEvent
        )
    }.onCompletion {
        when (it) {
            is SuccessCompletionException -> {
                statsQueue.remove(job = currentCoroutineContext().job)
            }
        }
    }.flowOn(ioDispatcher)
}

