package com.wepli.shared.feature.uimodel.user

import com.wepli.shared.feature.common.UiModel
import com.wepli.shared.feature.common.UiModelMapper
import kotlinx.parcelize.Parcelize
import model.tendency.Tendency
import model.user.User

@Parcelize
data class UserUiData(
    val id: String,
    val nickname: String,
    val email: String,
    val profileImgUrl: String,
    val tendency: Tendency,
) : UiModel {

    constructor() : this("", "", "", "", Tendency.BASIC_RHYTHM)

    companion object : UiModelMapper<User, UserUiData> {
        override fun fromDomain(domainModel: User): UserUiData {
            return UserUiData(
                id = domainModel.id,
                nickname = domainModel.nickname,
                email = domainModel.email,
                profileImgUrl = domainModel.profileImgUrl,
                tendency = domainModel.tendency
            )
        }

        override fun toDomain(uiModel: UserUiData): User {
            return User(
                id = uiModel.id,
                nickname = uiModel.nickname,
                email = uiModel.email,
                profileImgUrl = uiModel.profileImgUrl,
                tendency = uiModel.tendency
            )
        }
    }
}