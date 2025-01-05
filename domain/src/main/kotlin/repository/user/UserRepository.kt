package repository.user

import model.user.User
import java.time.Instant

interface UserRepository {

    suspend fun getUser(): User?
    suspend fun setUserData(user: User)

    suspend fun saveUserSession(
        accessToken: String,
        refreshToken: String,
        expiredAt: Instant,
    )

    suspend fun isUserSessionValid(): Boolean

    suspend fun clearUserData()
}