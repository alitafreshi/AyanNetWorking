package com.alitafreshi.ayan_networking.state_strategy

import com.alitafreshi.ayan_networking.state_handling.RequestGenericState
import kotlinx.coroutines.Job

interface StateHandlingUseCase<T> {
    fun isEmpty(): Boolean
    fun count(): Int
    fun addOrUpdate(element: RequestGenericState<T>)
    fun remove(job: Job): Boolean
    fun findItem(condition: (item: RequestGenericState<T>) -> Boolean): RequestGenericState<T>?
    fun handleCancellation(uiComponent: T, throwable: Throwable? = null, stateEvent: Any? = null)
    fun cancelByUser()
    fun clear()
}