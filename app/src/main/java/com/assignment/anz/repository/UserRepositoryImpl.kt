package com.assignment.anz.repository

import com.assignment.anz.model.User
import com.assignment.anz.network.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {
    override fun getUsers(): Flow<Result<List<User>>> = flow {
        try {
            val users = apiService.getUsers()
            emit(Result.success(users))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
