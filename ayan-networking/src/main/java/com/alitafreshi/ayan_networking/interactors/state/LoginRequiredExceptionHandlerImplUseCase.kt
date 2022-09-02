package com.alitafreshi.ayan_networking.interactors.state

import com.alitafreshi.ayan_networking.state_handling.RequestGenericState
import com.alitafreshi.ayan_networking.state_handling.RequestState
import com.alitafreshi.ayan_networking.state_strategy.ExceptionHandler

class LoginRequiredExceptionHandlerImplUseCase<T> : ExceptionHandler<T> {
    override operator fun invoke(
        currentRequests: MutableList<RequestGenericState<T>>,
        throwable: Throwable?,
        uiComponent: T,
        stateEvent: Any?
    ): MutableList<RequestGenericState<T>> = currentRequests.map {

        it.job?.cancel()

        if (it.stateEvent == stateEvent)
            it.copy(
                uiComponent = uiComponent,
                job = null,
                requestState = RequestState.Error(throwable = throwable)
            )
        else
            it.copy(
                job = null,
                stateEvent = null,
            )


    }.toMutableList()
}