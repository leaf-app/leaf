package ru.dzgeorgy.auth

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dzgeorgy.auth.data.network.NetworkService
import ru.dzgeorgy.core.LiveEvent
import ru.dzgeorgy.core.account.AccountUtils
import ru.dzgeorgy.core.network.Network

class LoginViewModel @ViewModelInject constructor(
    private val accountUtils: AccountUtils,
    private val network: Network
) : ViewModel() {

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
            val data = getAccountInfo(id, token)
            _status.value = R.string.status_create_account
            accountUtils.createAccount(id, token, data)
            userData.set(data)
            _moveToWelcome.value = true
        }
    }

    private suspend fun getAccountInfo(id: Int, token: String) = withContext(Dispatchers.IO) {
        val service: NetworkService = network.createService()
        service.get(id, token).response[0]
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
