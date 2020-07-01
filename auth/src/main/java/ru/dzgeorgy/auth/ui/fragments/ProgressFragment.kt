package ru.dzgeorgy.auth.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.auth.LoginViewModel
import ru.dzgeorgy.auth.R
import ru.dzgeorgy.auth.databinding.FragmentProgressBinding

@AndroidEntryPoint
class ProgressFragment : Fragment() {

    private lateinit var binding: FragmentProgressBinding
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressBinding.inflate(layoutInflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ProgressFragment.viewModel
        }
        viewModel.moveToWelcome.observe(viewLifecycleOwner, Observer {
            if (it) findNavController().navigate(R.id.progress_to_welcome)
        })
        return binding.root
    }

}