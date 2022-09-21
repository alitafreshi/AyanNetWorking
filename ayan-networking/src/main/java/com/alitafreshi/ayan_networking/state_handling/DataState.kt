package com.alitafreshi.ayan_networking.state_handling

sealed class DataState<DataType, StateEvent> {

    data class Error<DataType, StateEvent>(
        val uiComponent: UIComponent,
        val stateEvent: StateEvent,
        val throwable: Throwable
    ) : DataState<DataType, StateEvent>()

    data class Data<DataType, StateEvent>(
        val data: DataType? = null,
        val stateEvent: StateEvent
    ) : DataState<DataType, StateEvent>()

    data class Loading<DataType, StateEvent>(
        val bottomSheetState: BottomSheetState = BottomSheetState.Idle,
        val stateEvent: StateEvent
    ) : DataState<DataType, StateEvent>()
}
