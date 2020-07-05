package ru.dzgeorgy.leaf

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.transition.platform.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val transition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.apply {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            exitTransition = transition
            enterTransition = transition
            allowEnterTransitionOverlap = true
        }
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
        startActivity(Intent(Intent.ACTION_VIEW, uri))
        finish()
    }

}