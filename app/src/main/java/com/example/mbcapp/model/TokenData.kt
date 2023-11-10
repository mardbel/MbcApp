package com.example.mbcapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenData(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("attributes") val attributes: Token
) : Parcelable
