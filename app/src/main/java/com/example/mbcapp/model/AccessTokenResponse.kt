package com.example.mbcapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccessTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val secondsUntilExpiration: Int,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("created_at") val createdAt: Int
) : Parcelable
