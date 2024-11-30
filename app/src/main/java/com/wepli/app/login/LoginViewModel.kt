package com.wepli.app.login

import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.viewModelScope
import base.BaseViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.wepli.app.BuildConfig
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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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
    val genreItems: List<String>
)

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    // Effect를 위한 Channel
    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

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
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256").digest(bytes)
        val hashedNonce = messageDigest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.SUPABASE_CLIENT_ID)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val credential = getCredential(request).credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                supabase.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }
            }.onSuccess {
                val session = supabase.auth.currentSessionOrNull()
                session?.expiresAt
                session?.accessToken
                session?.refreshToken

                // 로그인 성공
                _effect.send(LoginEffect.NavigateToMain)
            }.onFailure {
                // 로그인 실패
                _effect.send(LoginEffect.ShowToast("로그인 실패: ${it.message}"))
            }
        }
    }
}