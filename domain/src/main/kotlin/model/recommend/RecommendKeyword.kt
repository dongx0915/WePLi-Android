package model.recommend

data class RecommendKeyword(
    val subject: String,
    val keywords: List<Keyword>
) {
    data class Keyword(
        val text: String,
        val isHighlightTag: Boolean
    )
}
