package ru.dzgeorgy.leaf

import android.app.Application
import android.content.Intent
import dagger.hilt.android.HiltAndroidApp
import ru.dzgeorgy.auth.ui.activity.LoginActivity

@HiltAndroidApp
class Leaf : Application() {

    override fun onCreate() {
        super.onCreate()
        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            ).also { it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
    }

}