package model.playlist

/**
 * 추천 플레이리스트 정보
 *
 * @property title 제목
 * @property coverImgUrl 커버 이미지 URL
 */
data class RecommendPlaylist(
    val title: String,
    val coverImgUrl: String,
)
