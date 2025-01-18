package com.wepli.data.keyword.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.recommend.RecommendKeyword

@Serializable
data class RecommendKeywordResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("subject")
    val subject: String,
    @SerialName("text")
    val text: String,
    @SerialName("is_highlight_tag")
    val isHighlightTag: Boolean,
)

fun List<RecommendKeywordResponse>.toEntityList(): List<RecommendKeyword> {
    return this.groupBy { it.subject }
        .map { (subject, keyword) ->
            RecommendKeyword(
                subject = subject,
                keywords = keyword.map {
                    RecommendKeyword.Keyword(
                        text = it.text,
                        isHighlightTag = it.isHighlightTag
                    )
                }
            )
        }
}