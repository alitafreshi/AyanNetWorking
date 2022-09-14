package com.alitafreshi.ayan_networking.state_handling

sealed class DataState<T> {

    data class Error<T>(
        val uiComponent: UIComponent
    ) : DataState<T>()

    data class Data<T>(
        val data: T? = null
    ) : DataState<T>()

    data class Loading<T>(
        val bottomSheetState: BottomSheetState = BottomSheetState.Idle
    ) : DataState<T>()
}