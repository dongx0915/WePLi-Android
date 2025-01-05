package com.wepli.data.user

import com.wepli.data.datastore.DataStoreKey
import com.wepli.data.datastore.local.DataStorePrefDataSource
import extensions.parseFromJson
import extensions.toJsonString
import model.user.User
import repository.user.UserRepository
import java.time.Instant
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataStorePrefDataSource: DataStorePrefDataSource
) : UserRepository {

    override suspend fun getUser(): User? {
        return dataStorePrefDataSource.getString(DataStoreKey.USER, "").parseFromJson<User>()
    }

    override suspend fun setUserData(user: User) {
        dataStorePrefDataSource.setString(DataStoreKey.USER, user.toJsonString())
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
}