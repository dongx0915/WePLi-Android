package model.user

import common.DomainModel
import model.tendency.Tendency

data class User(
    val id: String,
    val nickname: String,
    val email: String,
    val profileImgUrl: String,
    val tendency: Tendency,
) : DomainModel