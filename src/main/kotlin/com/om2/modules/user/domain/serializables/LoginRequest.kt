package com.om2.modules.user.domain.serializables

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)