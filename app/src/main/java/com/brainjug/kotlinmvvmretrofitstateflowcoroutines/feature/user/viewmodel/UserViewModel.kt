package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.UserInterface
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.response.User
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.RepoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userInterface: UserInterface) : ViewModel(){

    private val _listUser = MutableStateFlow(UsersUiState.Success(emptyList()))
    val listUser: StateFlow<UsersUiState> = _listUser

    init {
        getUserList()
    }

    private fun getUserList() {
        viewModelScope.launch {
            userInterface.getUsers().collect { result ->
                when(result){
                    is RepoResult.Success -> {
                        result.data?.let {
                            UsersUiState.Loading(false)
                            _listUser.value = UsersUiState.Success(it)
                        }
                    }
                    is RepoResult.Loading -> {
                        UsersUiState.Loading(true)
                    }
                    is RepoResult.Error -> {
                        UsersUiState.Error("error")
                    }
                    is RepoResult.Exception -> {
                        UsersUiState.Exception("exception")
                    }
                }
            }
        }
    }

    sealed class UsersUiState {
        data class Success(val news: List<User>): UsersUiState()
        data class Error(val error: String): UsersUiState()
        data class Loading(val isLoading: Boolean): UsersUiState()
        data class Exception(val exception: String): UsersUiState()
    }
}
