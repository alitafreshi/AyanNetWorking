package com.alitafreshi.ayan_networking.state_handling

import kotlinx.coroutines.Job

data class RequestGenericState<UIComponent>(
    val uiComponent: UIComponent?,
    val stateEvent: Any?,
    val job: Job?,
    val requestState: RequestState
)

sealed class RequestState {

    object Loading : RequestState()

    data class Error(val throwable: Throwable? = null) : RequestState()

    data class Pending(val pendingType: PendingType = PendingType.None) : RequestState()
}


/**
 * Each request can pend on a [JobPending] , [TokenPending] .
 * if a request needs to be launched independent from these condition and in Parallel to other requests we can use [None]*/

sealed class PendingType {
    data class JobPending(val job: Job) : PendingType()
    object TokenPending : PendingType()
    object None : PendingType()
}

