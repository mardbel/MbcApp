package com.example.mbcapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.mbcapp.model.AccessTokenResponse

class SharePreferencesProvider(private var context: Context) {

    private val KEY_TOKEN = "token"

    fun saveToken(value: AccessTokenResponse) {
        val settings: SharedPreferences = context.getSharedPreferences(
            KEY_TOKEN, 0
        )
        val editor = settings.edit()
        editor.putString("accessToken", value.accessToken)
        editor.putInt("secondsUntilExpiration", value.secondsUntilExpiration)
        editor.putString("refreshToken", value.refreshToken)
        editor.putInt("createdAt", value.createdAt)
        editor.apply()
    }

    fun getValidToken(): AccessTokenResponse {
        val settings: SharedPreferences = context.getSharedPreferences(
            KEY_TOKEN, 0
        )
        val accessToken = settings.getString("accessToken", "")
        val secondsUntilExpiration = settings.getInt("secondsUntilExpiration", 0)
        val refreshToken = settings.getString("refreshToken", "")
        val createdAt = settings.getInt("createdAt", 0)
        return AccessTokenResponse(
            accessToken = accessToken!!,
            secondsUntilExpiration = secondsUntilExpiration,
            tokenType = "Bearer",
            refreshToken = refreshToken!!,
            createdAt = createdAt
        )
    }
}