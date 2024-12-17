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
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
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
class LoginViewModel @Inject constructor() : ContainerHost<LoginState, LoginEffect>, BaseViewModel() {

    override val container: Container<LoginState, LoginEffect> = container(initialState = LoginState(emptyList()))

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState(emptyList()))
    val state: StateFlow<LoginState> = _state.asStateFlow()

    // Effect를 위한 Channel
    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        _state.update {
            it.copy(albumImages = recommendPlaylistMockData.map { it.coverImgUrl })
        }
    }

    private val supabase: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            install(Auth)
            install(Postgrest)
        }
    }

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.RequestGoogleLogin -> requestGoogleLogin(intent.getCredential)
        }
    }

    private fun requestGoogleLogin(
        getCredential: suspend (GetCredentialRequest) -> GetCredentialResponse
    ) {
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

                _effect.send(LoginEffect.NavigateToMain)
            }.onFailure {
                _effect.send(LoginEffect.ShowToast("로그인 실패: ${it.message}"))
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