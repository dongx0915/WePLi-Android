package com.wepli.data.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.tendency.Tendency
import model.user.User

@Serializable
data class UserResponse(
    @SerialName("id")
    val id: String? = null,
    @SerialName("username")
    val nickname: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("profile_img")
    val profileImgUrl: String? = null,
    @SerialName("tendency")
    val tendency: String? = null,
)

fun UserResponse.toUser() = User(
    id = id.orEmpty(),
    nickname = nickname.orEmpty(),
    email = email.orEmpty(),
    profileImgUrl = profileImgUrl.orEmpty(),
    tendency = Tendency.from(tendency)
)