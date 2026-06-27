package com.assignment.anz.repository

import com.assignment.anz.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Result<List<User>>>
}
