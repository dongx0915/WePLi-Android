package repository.post

import com.wepli.core.kotlin.FlowResult
import model.community.Post

interface PostRepository {

    fun getPosts(): FlowResult<List<Post>>
    fun addPost(post: Post): FlowResult<Unit>
}