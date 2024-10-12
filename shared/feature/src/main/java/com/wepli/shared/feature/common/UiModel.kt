package com.wepli.common

import android.os.Parcelable
import common.DomainModel

interface UiModel : Parcelable

// 제네릭 타입을 사용한 UiModelMapper 인터페이스
interface UiModelMapper<D : DomainModel, U : UiModel> {
    fun fromDomain(domainModel: D): U
}