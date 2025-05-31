package repository.user

import com.wepli.core.kotlin.flow.FlowResult
import kotlinx.coroutines.flow.Flow
import model.user.User
import java.time.Instant

interface UserRepository {

    suspend fun getUserById(id: String): FlowResult<User>

    fun getUserFlow(): Flow<User?>
    suspend fun getUserLocalData(): User?
    suspend fun setUserLocalData(user: User)
    suspend fun updateUserData(user: User): FlowResult<Unit>

    suspend fun getRefreshToken(): String
    suspend fun saveUserSession(
        accessToken: String,
        refreshToken: String,
        expiredAt: Instant,
    )

    suspend fun isUserSessionValid(): Boolean

    suspend fun clearUserData()
}