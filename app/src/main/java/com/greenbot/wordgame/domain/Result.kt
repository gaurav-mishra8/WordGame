package com.greenbot.wordgame.domain

sealed interface Result<out T> {
    class Success<T>(val data: T) : Result<T>
    class Error(val errorMsg: String) : Result<Nothing>
    object Loading : Result<Nothing>
}