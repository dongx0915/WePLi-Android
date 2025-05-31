package model.user.usecase

import com.wepli.core.kotlin.flow.FlowResult
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import model.supabase.repository.SupabaseBucketRepository
import model.user.User
import repository.user.UserRepository
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val supabaseBucketRepository: SupabaseBucketRepository,
) {

    suspend operator fun invoke(
        user: User,
        imageData: ByteArray?,
        fileExtension: String?
    ): FlowResult<Unit> {
        return if (imageData != null && fileExtension != null) {
            supabaseBucketRepository.uploadFile(
                bucketName = "profile",
                file = imageData,
                extension = fileExtension,
            ).flatMapConcat {
                it.fold(
                    onSuccess = {
                        val updatedUser = user.copy(profileImgUrl = it.path)

                        userRepository.updateUserData(updatedUser)
                    },
                    onFailure = {
                        flowOf(Result.failure(it))
                    }
                )
            }
        } else {
            userRepository.updateUserData(user)
        }
    }
}