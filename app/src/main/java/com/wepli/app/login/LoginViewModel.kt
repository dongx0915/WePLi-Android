package com.wepli.app.login

import androidx.credentials.Credential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.viewModelScope
import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.wepli.core.common.BuildConfig
import com.wepli.shared.feature.mock.recommendPlaylistMockData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.toJavaInstant
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import model.user.User
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
                postSideEffect(LoginEffect.NavigateToMain)
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
                postSideEffect(LoginEffect.GoogleLoginError(it.message.toString()))
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
            val userMetadataJson: JsonObject = user.userMetadata ?: return@withContext false

            val userNickname: String = userMetadataJson["name"]?.jsonPrimitive?.contentOrNull.orEmpty()
            val userAvatarUrl: String = userMetadataJson["avatar_url"]?.jsonPrimitive?.contentOrNull.orEmpty()

            with(userRepository) {
                saveUserSession(
                    accessToken = session.accessToken,
                    refreshToken = session.refreshToken,
                    expiredAt = session.expiresAt.toJavaInstant()
                )
                setUserData(
                    User(
                        email = user.email.orEmpty(),
                        nickname = userNickname,
                        profileImgUrl = userAvatarUrl
                    )
                )
            }

            true
        }
    }
}