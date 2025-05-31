package com.wepli.data.user.datasource

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.user.response.UserResponse
import model.user.User

interface UserSupabaseDataSource {

    suspend fun getUserById(id: String): FlowResult<UserResponse>

    suspend fun updateUser(user: User): FlowResult<Unit>
}