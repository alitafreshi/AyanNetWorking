package com.alitafreshi.ayan_networking.state_strategy

import com.alitafreshi.ayan_networking.state_handling.RequestGenericState

interface ExceptionHandler<T> {
    operator fun invoke(
        currentRequests: MutableList<RequestGenericState<T>>,
        throwable: Throwable?,
        uiComponent: T,
        stateEvent: Any? = null
    ): MutableList<RequestGenericState<T>>
}