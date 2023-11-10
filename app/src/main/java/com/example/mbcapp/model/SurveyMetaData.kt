package com.example.mbcapp.model

import com.google.gson.annotations.SerializedName

data class SurveyMetaData(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("page_size") val page_size: Int,
    @SerializedName("records") val records: Int
)
