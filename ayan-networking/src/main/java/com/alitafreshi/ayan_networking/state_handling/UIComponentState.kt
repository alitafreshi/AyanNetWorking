package com.alitafreshi.ayan_networking.state_handling

sealed class UIComponentState {
    object Loading : UIComponentState()
    object Idle : UIComponentState()
}