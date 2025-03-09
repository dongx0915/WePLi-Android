package com.wepli.data.post.datasource

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.post.response.PostResponse
import model.community.Post

interface PostDataSource {

    fun getPosts(): FlowResult<List<PostResponse>>
    fun addPost(post: Post): FlowResult<Unit>
}