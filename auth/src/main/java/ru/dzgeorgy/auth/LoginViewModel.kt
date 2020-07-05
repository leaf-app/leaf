package ru.dzgeorgy.auth

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import ru.dzgeorgy.core.LiveEvent
import ru.dzgeorgy.core.account.AccountUtils

class LoginViewModel @ViewModelInject constructor(
    private val accountUtils: AccountUtils,
    @ApplicationContext private val context: Context
) :
    ViewModel() {

    //Navigation Events
    private val _moveToWeb = LiveEvent<Boolean>()
    val moveToWeb: LiveData<Boolean>
        get() = _moveToWeb

    private val _moveToProgress = LiveEvent<Boolean>()
    val moveToProgress: LiveData<Boolean>
        get() = _moveToProgress

    private val _moveToWelcome = LiveEvent<Boolean>()
    val moveToWelcome: LiveData<Boolean>
        get() = _moveToWelcome

    private val _moveToMainActivity = LiveEvent<Boolean>()
    val moveToMainActivity: LiveData<Boolean>
        get() = _moveToMainActivity

    //UI data
    private val _status =
        MutableLiveData<@androidx.annotation.StringRes Int>(R.string.status_get_data)
    val status: LiveData<Int>
        get() = _status

    val directAuthEnabled =
        ObservableBoolean(Firebase.remoteConfig.getBoolean("direct_auth_enabled"))

    val userData = ObservableField<AccountUtils.AccountInfo>()

    private val _error =
        MutableLiveData<Triple<@androidx.annotation.StringRes Int, @androidx.annotation.StringRes Int, String?>?>(
            null
        )
    val error: LiveData<Triple<Int, Int, String?>?>
        get() = _error

    //UI interaction
    fun onVkSignInClick() {
        _moveToWeb.value = true
    }

    fun onStartClick() {
        _moveToMainActivity.value = true
    }

    @SuppressLint("NullSafeMutableLiveData") //Suppress cause of bug with Lint in AS 4.2 Canary 3
    fun onAlertDialogShow() {
        _error.value = null
    }

    //Authentication stuff
    fun onLoginSuccess(token: String, id: Int) {
        _moveToProgress.value = true

        viewModelScope.launch {
            val data = getAccountInfo(
                mapOf(
                    "user_ids" to "$id",
                    "access_token" to token,
                    "fields" to "photo_max, status",
                    "v" to ru.dzgeorgy.core.BuildConfig.VK_API_VERSION,
                    "lang" to context.getString(R.string.locale)
                )
            )
            _status.value = R.string.status_create_account
            accountUtils.createAccount(id, token, data)
            userData.set(data)
            _moveToWelcome.value = true
        }
    }

    @Serializable
    data class ResponseArray<T>(
        val response: List<@ContextualSerialization AccountUtils.AccountInfo>
    )

    private suspend fun getAccountInfo(params: Map<String, String>) =
        withContext(Dispatchers.IO) {
            val httpClient = HttpClient(OkHttp) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
                install(JsonFeature) {
                    serializer =
                        KotlinxSerializer(Json(JsonConfiguration(ignoreUnknownKeys = true)))
                }
            }
            val uri = "https://api.vk.com/method/users.get?"
            httpClient.get<ResponseArray<AccountUtils.AccountInfo>>(uri) {
                params.forEach { (k, v) ->
                    url.parameters.append(k, v)
                }
            }.response[0]
        }

    fun onLoginFail(description: String) {
        _error.value = when (description) {
            "access_denied" -> Triple(
                R.string.access_denied_err,
                R.string.access_denied_message,
                null
            )
            "net::ERR_INTERNET_DISCONNECTED" -> Triple(
                R.string.no_internet_err,
                R.string.no_internet_message,
                null
            )
            else -> Triple(R.string.unknown_error, R.string.unknown_error_message, null)
        }
    }
}
