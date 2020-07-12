package ru.dzgeorgy.leaf.ui.activities

import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.platform.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.leaf.databinding.ActivityMainBinding
import ru.dzgeorgy.leaf.ui.dialogs.BottomMenuDialog
import ru.dzgeorgy.leaf.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
            bottomAppbar.setNavigationOnClickListener {
                val dialog = BottomMenuDialog()
                dialog.show(supportFragmentManager, "bottom_menu")
            }
        }
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        //Workaround on loading startDestination in childFragment
//        val fragment =
//            supportFragmentManager.fragments[0].childFragmentManager.fragments[0] as LeafFragment
//        viewModel.initFragmentInteractions(
//            fragment.onFabClick,
//            fragment.fabIcon,
//            fragment.fabAlignment,
//            fragment.menu,
//            fragment.onMenuClick
//        )
        //Registering callbacks
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            viewModel.FragmentCallbacks(),
            true
        )
    }

}