package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking

sealed class RepoResult<out T> {
    data class Success<out T>(val data: T) : RepoResult<T>()
    data class Error(val code: Int, val data: String) : RepoResult<Nothing>()
    data class Loading(val loadingStatus: Boolean) : RepoResult<Nothing>()
    data class Exception(val throwable: Throwable) : RepoResult<Nothing>()
}