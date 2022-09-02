package com.alitafreshi.ayan_networking.state_handling

sealed class BottomSheetState {
    object Loading : BottomSheetState()
    object Idle : BottomSheetState()
}