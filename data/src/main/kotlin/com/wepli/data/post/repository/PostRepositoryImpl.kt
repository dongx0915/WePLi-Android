package com.wepli.data.post.repository

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.post.datasource.PostDataSource
import model.community.Post
import repository.post.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    @SupabaseDataSource private val postDataSource: PostDataSource,
) : PostRepository {

    override fun addPost(post: Post): FlowResult<Unit> {
        return postDataSource.addPost(post)
    }
}