package ru.dzgeorgy.leaf.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dzgeorgy.core.account.AccountUtils

class MainViewModel @ViewModelInject constructor(
    private val accountUtils: AccountUtils
) : ViewModel() {

    private val _userData = MutableLiveData<AccountUtils.AccountInfo>()
    val userData: LiveData<AccountUtils.AccountInfo>
        get() = _userData

    init {
        viewModelScope.launch {
            _userData.value = accountUtils.getActive()!!
        }
    }

}