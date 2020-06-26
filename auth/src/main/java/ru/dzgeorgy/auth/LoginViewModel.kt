package ru.dzgeorgy.auth

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import ru.dzgeorgy.auth.data.LeafAuthenticator

class LoginViewModel @ViewModelInject constructor(private val authenticator: LeafAuthenticator) :
    ViewModel() {

    val directAuthEnabled =
        ObservableBoolean(Firebase.remoteConfig.getBoolean("direct_auth_enabled"))

    fun onVkSignInClick() {

    }

}
