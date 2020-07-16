package ru.dzgeorgy.leaf.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.dzgeorgy.core.LiveEvent
import ru.dzgeorgy.core.account.User

class SplashViewModel @ViewModelInject constructor(
    private val user: User?
) :
    ViewModel() {

    private val _moveToLogin = LiveEvent<Boolean>()
    val moveToLogin: LiveData<Boolean>
        get() = _moveToLogin

    private val _moveToMain = LiveEvent<Boolean>()
    val moveToMain: LiveData<Boolean>
        get() = _moveToMain

    fun checkAccount() {
//        viewModelScope.launch {
//            if (accountUtils.getActive() == null) withContext(Dispatchers.Main) {
//                _moveToLogin.value = true
//            }
//            else withContext(Dispatchers.Main) { _moveToMain.value = true }
//        }
        if (user == null) _moveToLogin.value = true
        else _moveToMain.value = true
    }

}