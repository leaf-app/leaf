package ru.dzgeorgy.auth.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.auth.data.LeafAuthenticator
import javax.inject.Inject

@AndroidEntryPoint
class AuthenticatorService : Service() {

    @Inject
    lateinit var authenticator: LeafAuthenticator// = LeafAuthenticator(applicationContext)

    override fun onBind(p0: Intent?): IBinder? =
        authenticator.iBinder
}