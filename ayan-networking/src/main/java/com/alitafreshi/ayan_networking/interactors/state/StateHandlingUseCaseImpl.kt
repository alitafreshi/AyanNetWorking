package com.alitafreshi.ayan_networking.interactors.state

import com.alitafreshi.ayan_networking.constants.exceptions.AyanLocalException
import com.alitafreshi.ayan_networking.constants.exceptions.AyanServerException
import com.alitafreshi.ayan_networking.state_handling.RequestGenericState
import com.alitafreshi.ayan_networking.state_handling.RequestState
import com.alitafreshi.ayan_networking.state_strategy.ExceptionHandler
import com.alitafreshi.ayan_networking.state_strategy.StateHandlingUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet

class StateHandlingUseCaseImpl<T>(
    private val loginRequiredExceptionHandler: ExceptionHandler<T>,
    private val unexpectedExceptionHandler: ExceptionHandler<T>
) : StateHandlingUseCase<T> {

    private val _items = MutableStateFlow(value = mutableListOf<RequestGenericState<T>>())

    val items = _items.asStateFlow()

    override fun toString() = _items.value.toString()

    override fun isEmpty(): Boolean = _items.value.isEmpty()

    override fun count(): Int = _items.value.count()

    private fun add(element: RequestGenericState<T>) {
        _items.value.add(element)
    }

    override fun findItem(condition: (item: RequestGenericState<T>) -> Boolean): RequestGenericState<T>? =
        _items.value.find { condition(it) }

    override fun addOrUpdate(element: RequestGenericState<T>) {
        findItem { it.job == element.job }?.copy(
            uiComponent = element.uiComponent,
            job = element.job,
            requestState = element.requestState
        ) ?: add(element = element)
    }

    override fun remove(job: Job): Boolean {
        findItem { it.job == job }?.job?.cancel()
        return _items.value.removeIf { it.job == job }
    }

    override fun clear() {
        _items.updateAndGet {
            it.apply {
                it.removeAll { true }
            }
        }
    }

    //TODO This handleCancellation  Should Handel Each Exception like (LoginRequiredException or Etc) But We Can Do it in a clean way maybe Strategy Pattern
    override fun handleCancellation(uiComponent: T, throwable: Throwable?, stateEvent: Any?) {
        //TODO We need Each Logic For Each Exception Not One Handler For All Exceptions
        when (throwable) {
            is AyanServerException.LoginRequiredException, is AyanLocalException.DataStoreUnknownException -> {
                _items.updateAndGet {
                    loginRequiredExceptionHandler(
                        currentRequests = it,
                        throwable = throwable,
                        uiComponent = uiComponent
                    )
                }
            }
            //TODO We Don't Do Anything Here
            is AyanServerException.SuccessCompletionException -> {}

            else -> {
                //TODO May Be Some unexpected exceptions happen during the network call we should handle them here
                _items.updateAndGet {
                    unexpectedExceptionHandler(
                        currentRequests = it,
                        throwable = throwable,
                        uiComponent = uiComponent
                    )
                }
            }
        }
    }

    override fun cancelByUser() {
        _items.updateAndGet {
            it.map { requestItem ->
                requestItem.job?.cancel()
                requestItem.copy(
                    job = null,
                    requestState = RequestState.Error(
                        throwable = AyanLocalException.UserCancellationException(
                            message = "User Cancel The Request / job"
                        )
                    )
                )

            }.toMutableList()
        }
    }
}