package com.wepli.uimodel.community

import com.wepli.uimodel.music.SongUiData

/**
 * 게시글 정보
 * @property title 제목
 * @property content 내용
 * @property author 작성자
 * @property profileImg 작성자 프로필 이미지
 * @property songList 게시글에 포함된 음악 리스트
 */
data class PostUiData(
    val title: String,
    val content: String,
    val author: String,
    val profileImg: String,
    val songList: List<SongUiData>
)