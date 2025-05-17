package repository.user

import com.wepli.core.kotlin.flow.FlowResult
import model.user.User
import java.time.Instant

interface UserRepository {

    suspend fun getUserById(id: String): FlowResult<User>

    suspend fun getUserLocalData(): User?
    suspend fun setUserLocalData(user: User)

    suspend fun getRefreshToken(): String
    suspend fun saveUserSession(
        accessToken: String,
        refreshToken: String,
        expiredAt: Instant,
    )

    suspend fun isUserSessionValid(): Boolean

    suspend fun clearUserData()
}