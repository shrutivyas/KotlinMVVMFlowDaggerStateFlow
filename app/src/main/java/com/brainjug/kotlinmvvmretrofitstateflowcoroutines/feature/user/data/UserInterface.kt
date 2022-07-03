package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data

import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.response.User
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.RepoResult
import kotlinx.coroutines.flow.Flow

interface UserInterface {

    suspend fun getUsers(): Flow<RepoResult<List<User>>>
}