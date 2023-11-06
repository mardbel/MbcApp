package com.example.mbcapp.di

import com.example.mbcapp.remote.AuthenticationService
import com.example.mbcapp.repositories.UserAuthRepository
import com.example.mbcapp.repositories.UserAuthRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {


    @Provides
    @Singleton
    fun providesRetrofitClient(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(BaseUrls.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserAuthRepository(
        authenticationService: AuthenticationService
        ): UserAuthRepository {
        return UserAuthRepositoryImp(authenticationService)
    }

    @Provides
    @Singleton
    fun provideAuthService(): AuthenticationService = Retrofit.Builder()
        .baseUrl(BaseUrls.LOGIN_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthenticationService::class.java)

}