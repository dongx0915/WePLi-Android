package model.community

import common.DomainModel
import model.music.Song

/**
 * 게시글 정보
 * @property title 제목
 * @property content 내용
 * @property author 작성자
 * @property profileImg 작성자 프로필 이미지
 * @property songList 게시글에 포함된 음악 리스트
 */
data class Post(
    val title: String,
    val content: String,
    val author: String,
    val profileImg: String,
    val songList: List<Song>
) : DomainModel