package com.assignment.anz.di

import com.assignment.anz.network.UserApiService
import com.assignment.anz.repository.UserRepository
import com.assignment.anz.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://fake-json-api.mock.beeceptor.com"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUserApiService(): UserApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: UserApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }
}
