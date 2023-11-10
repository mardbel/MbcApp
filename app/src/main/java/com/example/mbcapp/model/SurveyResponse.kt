package com.example.mbcapp.model

data class SurveyResponse(
    val data: List<SurveyData>?,
    val metaData: SurveyMetaData?,
    val errors: List<Error>?
)
