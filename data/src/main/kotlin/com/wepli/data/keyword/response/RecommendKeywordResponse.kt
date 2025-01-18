package com.wepli.data.keyword.response

import kotlinx.serialization.Serializable
import model.recommend.RecommendKeyword

@Serializable
data class RecommendKeywordResponse(
    val subject: String,
    val keywords: List<Keyword>
) {

    @Serializable
    data class Keyword(
        val text: String,
        val isHighlightTag: Boolean
    )
}

fun RecommendKeywordResponse.toEntity(): RecommendKeyword {
    return RecommendKeyword(
        subject = subject,
        keywords = keywords.map {
            RecommendKeyword.Keyword(
                text = it.text,
                isHighlightTag = it.isHighlightTag
            )
        }
    )
}