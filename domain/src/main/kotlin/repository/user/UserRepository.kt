package repository.user

import com.wepli.core.kotlin.FlowResult
import model.user.User
import java.time.Instant

interface UserRepository {

    suspend fun getUserById(id: String): FlowResult<User>

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