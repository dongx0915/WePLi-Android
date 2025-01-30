package repository.post

import com.wepli.core.kotlin.FlowResult
import model.community.Post

interface PostRepository {

    fun addPost(post: Post): FlowResult<Unit>
}