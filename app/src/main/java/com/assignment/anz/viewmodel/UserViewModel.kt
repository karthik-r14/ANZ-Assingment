package com.assignment.anz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.anz.model.User
import com.assignment.anz.model.UserUiState
import com.assignment.anz.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    var selectedUser by mutableStateOf<User?>(null)
        private set

    fun selectUser(user: User) {
        selectedUser = user
    }

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            repository.getUsers().flowOn(Dispatchers.IO).collect { result ->
                _uiState.value = result.fold(
                    onSuccess = { UserUiState.Success(it) },
                    onFailure = { UserUiState.Error(it.message ?: "Unknown Error") })
            }
        }
    }
}
