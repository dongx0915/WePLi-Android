package com.wepli.shared.feature.uimodel.community

import com.wepli.common.UiModel
import com.wepli.common.UiModelMapper
import com.wepli.uimodel.music.SongUiData
import kotlinx.parcelize.Parcelize
import model.community.Post

/**
 * 게시글 정보
 * @property title 제목
 * @property content 내용
 * @property author 작성자
 * @property profileImg 작성자 프로필 이미지
 * @property songList 게시글에 포함된 음악 리스트
 */
@Parcelize
data class PostUiData(
    val title: String,
    val content: String,
    val author: String,
    val profileImg: String,
    val songList: List<SongUiData>
) : UiModel {

    constructor() : this("", "", "", "", emptyList())

    companion object : UiModelMapper<Post, PostUiData> {
        override fun fromDomain(domainModel: Post): PostUiData {
            return PostUiData(
                title = domainModel.title,
                content = domainModel.content,
                author = domainModel.author,
                profileImg = domainModel.profileImg,
                songList = domainModel.songList.map { SongUiData.fromDomain(it) }
            )
        }
    }
}