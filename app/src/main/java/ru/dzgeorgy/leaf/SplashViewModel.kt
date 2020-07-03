package ru.dzgeorgy.leaf

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dzgeorgy.core.LiveEvent
import ru.dzgeorgy.core.account.AccountUtils

class SplashViewModel @ViewModelInject constructor(private val accountUtils: AccountUtils) :
    ViewModel() {

    private val _moveToLogin = LiveEvent<Boolean>()
    val moveToLogin: LiveData<Boolean>
        get() = _moveToLogin

    private val _moveToMain = LiveEvent<Boolean>()
    val moveToMain: LiveData<Boolean>
        get() = _moveToMain

    fun checkAccount() {
        viewModelScope.launch {
            if (accountUtils.getActive() == null) withContext(Dispatchers.Main) {
                _moveToLogin.value = true
            }
            else withContext(Dispatchers.Main) { _moveToMain.value = true }
        }
    }

}