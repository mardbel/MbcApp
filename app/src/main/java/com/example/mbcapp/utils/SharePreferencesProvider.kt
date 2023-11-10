package com.example.mbcapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.mbcapp.model.Token
import com.example.mbcapp.model.TokenData
import com.example.mbcapp.model.TokenResponse

class SharePreferencesProvider(private var context: Context) {

    private val KEY_TOKEN = "token"

    fun saveToken(value: TokenResponse) {
        val settings: SharedPreferences = context.getSharedPreferences(
            KEY_TOKEN, 0
        )
        val editor = settings.edit()
        editor.putString("accessToken", value.data?.attributes?.accessToken)
        editor.putInt("secondsUntilExpiration", value.data?.attributes?.secondsUntilExpiration!!)
        editor.putString("refreshToken", value.data?.attributes?.refreshToken)
        editor.putInt("createdAt", value.data?.attributes?.createdAt!!)
        editor.apply()
    }

    fun getStoredToken(): Token {
        val settings: SharedPreferences = context.getSharedPreferences(
            KEY_TOKEN, 0
        )
        val accessToken = settings.getString("accessToken", "")
        val secondsUntilExpiration = settings.getInt("secondsUntilExpiration", 0)
        val refreshToken = settings.getString("refreshToken", "")
        val createdAt = settings.getInt("createdAt", 0)
        return Token(
            accessToken = accessToken!!,
            secondsUntilExpiration = secondsUntilExpiration,
            tokenType = "Bearer",
            refreshToken = refreshToken!!,
            createdAt = createdAt
        )
    }
}