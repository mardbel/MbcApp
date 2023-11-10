package com.example.mbcapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val secondsUntilExpiration: Int,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("created_at") val createdAt: Int
) : Parcelable
