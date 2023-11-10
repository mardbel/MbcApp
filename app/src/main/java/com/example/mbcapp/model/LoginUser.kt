package com.example.mbcapp.model

import com.example.mbcapp.BuildConfig
import com.google.gson.annotations.SerializedName

data class LoginUser(
    @SerializedName("grant_type") val grantType: String,
    @SerializedName("email") val email: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("client_id") val clientId: String = "ofzl-2h5ympKa0WqqTzqlVJUiRsxmXQmt5tkgrlWnOE",
    @SerializedName("client_secret") val clientSecret: String = "lMQb900L-mTeU-FVTCwyhjsfBwRCxwwbCitPob96cuU"
)
