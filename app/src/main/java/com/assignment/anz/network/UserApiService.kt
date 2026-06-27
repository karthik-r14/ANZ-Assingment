package com.assignment.anz.network

import com.assignment.anz.model.User
import retrofit2.http.GET

interface UserApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
