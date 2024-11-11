package model.user

import common.DomainModel

data class User(
    val nickname: String,
    val email: String,
    val profileImgUrl: String,
) : DomainModel