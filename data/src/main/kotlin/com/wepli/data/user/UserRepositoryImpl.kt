package com.wepli.data.user

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.datastore.DataStoreKey
import com.wepli.data.datastore.local.DataStorePrefDataSource
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.network.toEntityResult
import com.wepli.data.user.datasource.UserSupabaseDataSource
import com.wepli.data.user.response.toUser
import extensions.parseFromJson
import extensions.toJsonString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import model.user.User
import repository.user.UserRepository
import java.time.Instant
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @SupabaseDataSource private val userSupabaseDataSource: UserSupabaseDataSource,
    private val dataStorePrefDataSource: DataStorePrefDataSource
) : UserRepository {

    private var user: User? = null

    override fun getUserById(id: String): FlowResult<User> {
        return userSupabaseDataSource.getUserById(id).toEntityResult {
            it.toUser()
        }
    }

    override fun getUserFlow(): Flow<User?> {
        return dataStorePrefDataSource
            .getObjectFlow(DataStoreKey.USER, User::class.java)
            .onEach { user = it }
    }

    override suspend fun getUserLocalData(): User? {
        return user ?: dataStorePrefDataSource.getString(DataStoreKey.USER, "").parseFromJson<User>()
    }

    override suspend fun setUserLocalData(user: User) {
        dataStorePrefDataSource.setString(DataStoreKey.USER, user.toJsonString())
    }

    override fun updateUserData(user: User): FlowResult<Unit> {
        return userSupabaseDataSource.updateUser(user).onEach {
            it.onSuccess { setUserLocalData(user) }
        }
    }

    override suspend fun getRefreshToken(): String {
        return dataStorePrefDataSource.getString(DataStoreKey.REFRESH_TOKEN, "")
    }

    override suspend fun saveUserSession(accessToken: String, refreshToken: String, expiredAt: Instant) {
        dataStorePrefDataSource.apply {
            with(DataStoreKey) {
                setString(ACCESS_TOKEN, accessToken)
                setString(REFRESH_TOKEN, refreshToken)
                setLong(EXPIRED_AT, expiredAt.toEpochMilli())
            }
        }
    }

    override suspend fun isUserSessionValid(): Boolean {
        dataStorePrefDataSource.getLong(DataStoreKey.EXPIRED_AT, 0).let { expiredAt ->
            return Instant.ofEpochMilli(expiredAt).isAfter(Instant.now())
        }
    }

    override suspend fun clearUserData() {
        dataStorePrefDataSource.apply {
            with(DataStoreKey) {
                removeString(USER)
                removeString(ACCESS_TOKEN)
                removeString(REFRESH_TOKEN)
                removeLong(EXPIRED_AT)
            }
        }.also {
            user = null
        }
    }
}