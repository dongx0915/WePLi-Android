package com.wepli.data.post.repository

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.network.toEntityResult
import com.wepli.data.post.datasource.PostDataSource
import com.wepli.data.post.response.toPosts
import model.community.Post
import repository.post.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    @SupabaseDataSource private val postDataSource: PostDataSource,
) : PostRepository {

    override fun getPosts(): FlowResult<List<Post>> {
        return postDataSource.getPosts().toEntityResult { it.toPosts() }
    }

    override fun addPost(post: Post): FlowResult<Unit> {
        return postDataSource.addPost(post)
    }
}