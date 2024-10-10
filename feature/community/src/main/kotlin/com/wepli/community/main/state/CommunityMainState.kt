package com.wepli.community.main.state

import model.community.Post
import model.user.User

data class CommunityMainState(
    val storyUsers: List<User> = emptyList(),
    val posts: List<Post> = emptyList(),
)