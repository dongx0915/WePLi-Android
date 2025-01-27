package com.wepli.data.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.user.User

@Serializable
data class UserResponse(
    @SerialName("id")
    val id: String?,
    @SerialName("username")
    val nickname: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("profile_img")
    val profileImgUrl: String?,
)

fun UserResponse.toUser() = User(
    id = id.orEmpty(),
    nickname = nickname.orEmpty(),
    email = email.orEmpty(),
    profileImgUrl = profileImgUrl.orEmpty()
)