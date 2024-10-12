package model.user

import common.DomainModel

data class User(
    val nickname: String,
    val profileImgUrl: String,
) : DomainModel