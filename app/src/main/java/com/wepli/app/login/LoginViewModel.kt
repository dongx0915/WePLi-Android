package com.wepli.app.login

import androidx.credentials.Credential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.viewModelScope
import base.BaseViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.wepli.app.BuildConfig
import com.wepli.shared.feature.mock.recommendPlaylistMockData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

sealed interface LoginIntent {
    data class RequestGoogleLogin(
        val getCredential: suspend (GetCredentialRequest) -> GetCredentialResponse
    ) : LoginIntent
}

sealed interface LoginEffect {
    data class ShowToast(val message: String) : LoginEffect
    data object NavigateToMain : LoginEffect
}

data class LoginState(
    val albumImages: List<String>
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val supabase: SupabaseClient
) : ContainerHost<LoginState, LoginEffect>, BaseViewModel() {

    override val container: Container<LoginState, LoginEffect> = container(initialState = LoginState(emptyList()))

    init {
        loadAlbumImages()
    }

    fun processIntent(intent: LoginIntent) {
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
                /* TODO 자동 로그인 처리시 필요
                    val session = supabase.auth.currentSessionOrNull()
                    session?.expiresAt
                    session?.accessToken
                    session?.refreshToken
                */
                postSideEffect(LoginEffect.NavigateToMain)
            }.onFailure {
                postSideEffect(LoginEffect.ShowToast("로그인 실패: ${it.message}"))
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
}