package ru.dzgeorgy.auth.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.auth.LoginViewModel
import ru.dzgeorgy.auth.R
import ru.dzgeorgy.auth.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backward = MaterialSharedAxis(MaterialSharedAxis.X, false)
        reenterTransition = backward
        val forward = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = forward
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MainFragment.viewModel
        }
        viewModel.apply {
            moveToWeb.observe(viewLifecycleOwner, Observer {
                if (it) findNavController().navigate(R.id.main_to_web)
            })
            error.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(it.first)
                        .setMessage(getString(it.second, it.third))
                        .setPositiveButton(R.string.ok) { _, _ -> }
                        .show()
                    onAlertDialogShow()
                }
            })
        }
        return binding.root
    }

}