package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data

import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.response.User
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.BaseRepository
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.InternetConnectivity
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.RepoResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UserRepository @Inject constructor(private val internetConnectivity: InternetConnectivity,
                                               private val userApiService: UserApiService ) : UserInterface, BaseRepository(){

    override suspend fun getUsers(): Flow<RepoResult<List<User>>> {
        return if (internetConnectivity.isNetworkAvailable()) {
            safeApiCall(
                {
                    userApiService.getAllUsers()
                },
                { data ->
                    data
                }
            )
        } else {
            flowOf(RepoResult.Error(800, "No Internet connection"))
        }
    }
}