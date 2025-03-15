package model.recommend.repository

import com.wepli.core.kotlin.flow.FlowResult
import model.recommend.RecommendKeyword

interface KeywordRepository {

    fun getRecommendKeyword(): FlowResult<List<RecommendKeyword>>
}