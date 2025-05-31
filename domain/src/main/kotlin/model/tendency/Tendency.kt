package model.tendency

enum class Tendency(
    val title: String,
    val description: String,
) {
    BASIC_RHYTHM(
        title = "베이직 리듬",
        description = "음악을 편하게 즐기는 걸 좋아해요",
    ),
    SOCIAL_TUNES(
        title = "소셜 튠즈",
        description = "같이 노래 듣는 걸 좋아해요",
    ),
    MELODY_MEMORIES(
        title = "멜로디 메모리즈",
        description = "추억을 노래와 함께 간직하고 싶어요",
    ),
    NOSTALGIA_SOONER(
        title = "노스텔지어 서너",
        description = "옛날 감성의 노래를 좋아해요",
    ),
    SENTIMENTAL_SYMPHONY(
        title = "센티멘탈 심포니",
        description = "감성적인 노래를 좋아해요",
    ),
    ENERGY_FLASH(
        title = "에너지 플래시",
        description = "신나는 노래를 좋아해요",
    );

    companion object {
        fun from(value: String?): Tendency {
            return Tendency.entries.find { it.name == value } ?: BASIC_RHYTHM
        }
    }
}