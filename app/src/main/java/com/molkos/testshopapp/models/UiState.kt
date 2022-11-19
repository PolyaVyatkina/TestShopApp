package com.molkos.testshopapp.models

sealed class UiState {
    data class Success(val data: Any) : UiState()
    object Loading : UiState()
}
