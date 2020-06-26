package ru.dzgeorgy.leaf

import android.app.Application
import android.content.Intent
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import ru.dzgeorgy.auth.ui.activity.LoginActivity

@HiltAndroidApp
class Leaf : Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.remoteConfig.apply {
            setDefaultsAsync(ru.dzgeorgy.core.R.xml.remote_config_defaults)
            if (BuildConfig.DEBUG) remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 }
        }.fetchAndActivate().addOnCompleteListener {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                ).also { it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
        }
    }

}