package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class BaseRepository {

    fun <T, P> safeApiCall(
        apiCall: suspend () -> Response<T>,
        entityProcessor: (T) -> P,
    ): Flow<RepoResult<P>> = flow {
        emit(RepoResult.Loading(true))
        val flow = flowOf(safeApiCallInternal(apiCall, entityProcessor))
        emit(RepoResult.Loading(false))
        emitAll(flow)
    }.flowOn(Dispatchers.IO)

    private suspend fun <T, P> safeApiCallInternal(
        apiCall: suspend () -> Response<T>,
        entityProcessor: (T) -> P,
    ) = try {
        val apiResult = apiCall.invoke()
        if (apiResult.isSuccessful) {
            RepoResult.Success(entityProcessor(apiResult.body()!!))
        } else {
            RepoResult.Error(apiResult.code(), apiResult.message() ?: "")
        }
    } catch (ex: Exception) {
        RepoResult.Exception(ex)
    }

}