package com.wepli.app.login

import androidx.credentials.Credential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.viewModelScope
import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.wepli.core.common.BuildConfig
import com.wepli.core.kotlin.suspendCollectResult
import com.wepli.shared.feature.mock.recommendPlaylistMockData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.toJavaInstant
import repository.user.UserRepository
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

sealed interface LoginIntent: Intent {
    data class RequestGoogleLogin(
        val getCredential: suspend (GetCredentialRequest) -> GetCredentialResponse
    ) : LoginIntent
}

sealed interface LoginEffect: SideEffect{
    data class GoogleLoginError(val message: String) : LoginEffect
    data object GoogleSessionError : LoginEffect
    data object NavigateToMain : LoginEffect
    data object PromptAddGoogleAccount : LoginEffect // 계정 추가 유도
}

data class LoginState(
    val albumImages: List<String>
) : UiState

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val supabase: SupabaseClient,
    private val userRepository: UserRepository,
) : BaseMviViewModel<LoginState, LoginEffect, LoginIntent>(
    initialState = LoginState(albumImages = emptyList())
) {

    init {
        // TODO 자동 로그인은 추후 Splash로 이동 필요
        checkAutoLogin()
        loadAlbumImages()
    }

    private fun checkAutoLogin() = intent {
        viewModelScope.launch {
            if (userRepository.isUserSessionValid()) {
                runCatching {
                    val refreshToken = userRepository.getRefreshToken()
                    supabase.auth.refreshSession(refreshToken)
                }.onSuccess {
                    postSideEffect(LoginEffect.NavigateToMain)
                }.onFailure {
                    postSideEffect(LoginEffect.GoogleSessionError)
                }
            }
        }
    }

    override fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.RequestGoogleLogin -> requestGoogleLogin(intent.getCredential)
        }
    }

    private fun loadAlbumImages() = intent {
        val albumImages = recommendPlaylistMockData.map { it.coverImgUrl }
        reduce {
            state.copy(albumImages = albumImages)
        }
    }

    private fun requestGoogleLogin(
        getCredential: suspend (GetCredentialRequest) -> GetCredentialResponse
    ) = intent {
        val hashedNonce: String = generateHashNonce(UUID.randomUUID().toString())
        val request: GetCredentialRequest = buildGoogleLoginRequest(hashedNonce)

        viewModelScope.launch {
            runCatching {
                authenticateWithGoogle(request, getCredential, hashedNonce)
            }.onSuccess {
                val result = supabase.auth.currentSessionOrNull()?.run {
                    saveLoginResult(this)
                } ?: false

                if (result) {
                    postSideEffect(LoginEffect.NavigateToMain)
                } else {
                    postSideEffect(LoginEffect.GoogleSessionError)
                }
            }.onFailure {
                val errorEffect = when (it) {
                    is NoCredentialException -> LoginEffect.PromptAddGoogleAccount
                    else -> LoginEffect.GoogleLoginError(it.message.toString())
                }

                postSideEffect { errorEffect }
            }
        }
    }

    private fun generateHashNonce(rawNonce: String): String {
        val bytes = rawNonce.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256").digest(bytes)

        return messageDigest.fold("") { str, it -> str + "%02x".format(it) }
    }

    private fun buildGoogleLoginRequest(hashedNonce: String): GetCredentialRequest {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.SUPABASE_CLIENT_ID)
            .setNonce(hashedNonce)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    private suspend fun authenticateWithGoogle(
        request: GetCredentialRequest,
        getCredential: suspend (GetCredentialRequest) -> GetCredentialResponse,
        rawNonce: String,
    ): Unit = withContext(Dispatchers.IO) {
        val credential: Credential = getCredential(request).credential
        val googleIdTokenCredential: GoogleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
        val googleIdToken: String = googleIdTokenCredential.idToken

        supabase.auth.signInWith(IDToken) {
            idToken = googleIdToken
            provider = Google
            nonce = rawNonce
        }
    }

    private suspend fun saveLoginResult(session: UserSession): Boolean {
        return withContext(Dispatchers.IO) {
            val user: UserInfo = session.user ?: return@withContext false
            var isSuccess = false

            userRepository.getUserById(user.id)
                .flowOn(Dispatchers.IO)
                .suspendCollectResult(
                    onSuccess = { user ->
                        with(userRepository) {
                            saveUserSession(
                                accessToken = session.accessToken,
                                refreshToken = session.refreshToken,
                                expiredAt = session.expiresAt.toJavaInstant()
                            )
                            setUserData(user)
                        }

                        isSuccess = true
                    },
                    onFailure = {
                        isSuccess = false
                    }
                )

            return@withContext isSuccess
        }
    }
}