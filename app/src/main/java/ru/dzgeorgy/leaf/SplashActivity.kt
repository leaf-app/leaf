package ru.dzgeorgy.leaf

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            checkAccount()
            moveToLogin.observe(this@SplashActivity, Observer {
                if (it) navigate(Uri.parse("app://leaf/login"))
            })
            moveToMain.observe(this@SplashActivity, Observer {
                if (it) navigate(Uri.parse("app://leaf/main"))
            })
        }
    }

    private fun navigate(uri: Uri) {
        println(uri)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
        finish()
    }

}