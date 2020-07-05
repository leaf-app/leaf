package ru.dzgeorgy.leaf.ui.activities

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.platform.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.leaf.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main)
    }

}