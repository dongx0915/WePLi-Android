package model.user

import common.DomainModel

data class User(
    val id: String,
    val nickname: String,
    val email: String,
    val profileImgUrl: String,
) : DomainModel