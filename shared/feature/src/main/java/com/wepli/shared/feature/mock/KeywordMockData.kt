package com.wepli.shared.feature.mock

import model.recommend.RecommendKeyword

val keywordMockData: List<RecommendKeyword> = listOf(
    RecommendKeyword(
        subject = "저는 **노래를 들으면서 일해야**\n능률이 올라가는 편입니다",
        keywords = listOf(
            RecommendKeyword.Keyword("Line by Line", false),
            RecommendKeyword.Keyword("PREP", false),
            RecommendKeyword.Keyword("And July", true),
            RecommendKeyword.Keyword("헤이즈", false),
            RecommendKeyword.Keyword("Tom Misch", false),
            RecommendKeyword.Keyword("Movie", false),
            RecommendKeyword.Keyword("Disco Yes", true),
            RecommendKeyword.Keyword("Rush Hour", false),
            RecommendKeyword.Keyword("Crush", false),
        )
    ),
    RecommendKeyword(
        subject = "햇살 가득한 **여름날**\n이런 곡으로 추억을 완성해보세요!",
        keywords = listOf(
            RecommendKeyword.Keyword("여름", false),
            RecommendKeyword.Keyword("씨스타", false),
            RecommendKeyword.Keyword("바다", false),
            RecommendKeyword.Keyword("해변가", false),
            RecommendKeyword.Keyword("Sunshine", true),
            RecommendKeyword.Keyword("쿨", false),
            RecommendKeyword.Keyword("Summer", false),
            RecommendKeyword.Keyword("Waves", true),
        )
    ),
    RecommendKeyword(
        subject = "비 오는 날 **창밖을 보며**\n어울리는 음악을 찾아볼까요?",
        keywords = listOf(
            RecommendKeyword.Keyword("비", false),
            RecommendKeyword.Keyword("Rainy Day", true),
            RecommendKeyword.Keyword("우산", false),
            RecommendKeyword.Keyword("감성", true),
            RecommendKeyword.Keyword("Window", false),
            RecommendKeyword.Keyword("Puddle", false),
            RecommendKeyword.Keyword("윤하", false),
            RecommendKeyword.Keyword("Umbrella", false),
        )
    ),
    RecommendKeyword(
        subject = "도심 속에서 **느끼는 힐링**\n이런 곡은 어때요?",
        keywords = listOf(
            RecommendKeyword.Keyword("도심", false),
            RecommendKeyword.Keyword("힐링", true),
            RecommendKeyword.Keyword("야경", false),
            RecommendKeyword.Keyword("하늘", false),
            RecommendKeyword.Keyword("Urban", false),
            RecommendKeyword.Keyword("Skyline", false),
            RecommendKeyword.Keyword("Nightview", false),
            RecommendKeyword.Keyword("Relax", true),
        )
    )
)