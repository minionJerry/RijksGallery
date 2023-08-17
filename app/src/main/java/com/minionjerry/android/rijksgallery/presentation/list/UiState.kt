package com.minionjerry.android.rijksgallery.presentation.list

sealed class UiState<T : Any> {
    class Loading<T: Any> : UiState<T>()
    data class Error<T : Any>(val errorMessage: String) : UiState<T>()
    data class Success<T : Any>(val data: T) : UiState<T>()
}
