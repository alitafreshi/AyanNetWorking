package com.alitafreshi.ayan_networking.interactors.state

import com.alitafreshi.ayan_networking.state_handling.RequestGenericState
import com.alitafreshi.ayan_networking.state_handling.RequestState
import com.alitafreshi.ayan_networking.state_strategy.ExceptionHandler

class UnexpectedExceptionHandlerImpl<T> : ExceptionHandler<T> {
    override fun invoke(
        currentRequests: MutableList<RequestGenericState<T>>,
        throwable: Throwable?,
        uiComponent: T,
        stateEvent: Any?
    ): MutableList<RequestGenericState<T>> = currentRequests.map {
        if (it.stateEvent == stateEvent)
            it.copy(
                uiComponent = uiComponent,
                job = null,
                requestState = RequestState.Error(throwable = throwable),
                stateEvent = stateEvent
            )
        else
            it.copy(
                uiComponent = it.uiComponent,
                stateEvent = it.stateEvent,
                job = it.job,
                requestState = it.requestState
            )

    }.toMutableList()
}