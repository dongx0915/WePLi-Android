package com.wepli.shared.feature.uimodel.user

import com.wepli.common.UiModel
import com.wepli.common.UiModelMapper
import kotlinx.parcelize.Parcelize
import model.user.User

@Parcelize
data class UserUiData(
    val nickname: String,
    val email: String,
    val profileImgUrl: String,
): UiModel {

    constructor() : this("", "", "")

    companion object : UiModelMapper<User, UserUiData> {
        override fun fromDomain(domainModel: User): UserUiData {
            return UserUiData(
                nickname = domainModel.nickname,
                email = domainModel.email,
                profileImgUrl = domainModel.profileImgUrl,
            )
        }
    }
}