package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data

import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.response.User
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {

    @GET("v2/users")
    suspend fun getAllUsers(): Response<List<User>>
}