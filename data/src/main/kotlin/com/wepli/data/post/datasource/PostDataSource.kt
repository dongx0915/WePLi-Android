package com.wepli.data.post.datasource

import com.wepli.core.kotlin.FlowResult
import model.community.Post

interface PostDataSource {

    fun addPost(post: Post): FlowResult<Unit>
}