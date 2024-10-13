package com.wepli.shared.feature.mock

import com.wepli.uimodel.community.CommentUiData
import java.util.Date
import kotlin.random.Random

val commentMockData = fun(): List<CommentUiData>  {
    fun getRandomCommentContent(): String {
        val comments = listOf(
            "처음 들어보는데 완전 봄 느낌 물씬 나네요!",
            "이 곡 진짜 좋아요! 요즘 제 최애 곡입니다.",
            "노래 너무 좋아요. 덕분에 하루가 행복해졌어요!",
            "저도 이 노래 자주 들어요! 공유해줘서 고마워요!",
            "우와, 너무 좋네요! 바로 플레이리스트에 추가했습니다!",
            "정말 기분 좋아지는 곡이네요. 추천해주셔서 감사합니다!",
            "비 오는 날 들으면 더 좋은 곡이에요.",
            "아, 이거 제가 좋아하는 노래 중 하나네요!",
            "감사합니다! 좋은 노래 추천해주셔서!",
            "이 노래 들으니까 정말 기분이 좋아지네요~",
            "봄맞이로 딱 좋네요! 이런 신나는 곡 들으니까 겨울잠에서 깬 느낌ㅎㅎ",
            "봄노래 찾던 중에 이걸로 정착! 상큼터짐 최고ㅠㅠ",
            "처음 들어보는데 완전 봄 느낌 물씬 나네요! 기분 전환도 되고 화창한 봄날 거리를 걷는 기분이 들어요. 좋은 곡 공유해줘서 감사합니다!",
            "오~ 이거 듣고 나니까 진짜 봄이 온 기분ㅋㅋ 기분 전환하기 딱 좋아요! 여러분도 꼭 들어보세요!",
            "나도 이 노래 들었는데 진짜 봄 기운 물씬! 꽃길만 걷게 되는 느낌ㅋㅋ 다들 꽃구경 가면서 들으면 기분 최고일 듯!",
        )
        return comments.random() // 랜덤한 댓글 내용을 반환
    }

    fun getRandomCreatedAt(): Date {
        val currentTimeMillis = System.currentTimeMillis()
        val maxOffset = 30L * 24 * 60 * 60 * 1000 // 30일
        val randomTimeOffset = Random.nextLong(0, maxOffset + 1)
        return Date(currentTimeMillis - randomTimeOffset) // 0 ~ 30일 전
    }

    return userMockData.map { user ->
        CommentUiData(
            nickname = user.nickname,
            profileImg = user.profileImgUrl,
            content = getRandomCommentContent(),
            likeCount = Random.nextInt(0, 10000),
            replyCount = Random.nextInt(0, 8500),
            createdAt = getRandomCreatedAt()
        )
    }
}