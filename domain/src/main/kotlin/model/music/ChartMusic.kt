package model.music

/**
 * 차트 음악 정보
 *
 * @property rank 순위
 * @property title 제목
 * @property artist 아티스트
 * @property album 앨범
 * @property albumCoverUrl 앨범 커버 URL
 */
data class ChartMusic(
    val rank: Int,
    val title: String,
    val artist: String,
    val album: String,
    val albumCoverUrl: String,
)
