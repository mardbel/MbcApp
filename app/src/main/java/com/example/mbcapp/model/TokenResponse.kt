package com.example.mbcapp.model

data class TokenResponse(
    val data: TokenData?,
    val errors: List<Error>?
)
