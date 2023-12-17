package com.u1tramarinet.infolistapp.ui.screen

sealed class ScreenState<out T> {
    data object Loading : ScreenState<Nothing>()
    data class Success<out T>(val data: T) : ScreenState<T>()
    data class Error(val errorMessage: String) : ScreenState<Nothing>()
}