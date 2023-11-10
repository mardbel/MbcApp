package com.example.mbcapp.di

import android.app.Application
import com.example.mbcapp.remote.AuthenticationService
import com.example.mbcapp.remote.SurveyService
import com.example.mbcapp.repositories.SurveyRepository
import com.example.mbcapp.repositories.SurveyRepositoryImp
import com.example.mbcapp.repositories.UserAuthRepository
import com.example.mbcapp.repositories.UserAuthRepositoryImp
import com.example.mbcapp.utils.SharePreferencesProvider
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
        authenticationService: AuthenticationService,
        sharePreferencesProvider: SharePreferencesProvider
        ): UserAuthRepository {
        return UserAuthRepositoryImp(authenticationService, sharePreferencesProvider)
    }

    @Provides
    @Singleton
    fun provideSurveyRepository(
        surveyService: SurveyService,
        userRepository: UserAuthRepository,
    ): SurveyRepository {
        return SurveyRepositoryImp(surveyService, userRepository)
    }

    @Provides
    @Singleton
    fun provideSurveyService(): SurveyService = Retrofit.Builder()
        .baseUrl(BaseUrls.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SurveyService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(): AuthenticationService = Retrofit.Builder()
        .baseUrl(BaseUrls.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthenticationService::class.java)

    @Provides
    @Singleton
    fun provideSharePreferences(
        application: Application
    ): SharePreferencesProvider {
        return SharePreferencesProvider(application)
    }

}