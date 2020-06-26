package ru.dzgeorgy.auth.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.auth.LoginViewModel
import ru.dzgeorgy.auth.databinding.FragmentWebBinding

@AndroidEntryPoint
class WebFragment : Fragment() {

    private lateinit var binding: FragmentWebBinding
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@WebFragment.viewModel
        }
        return binding.root
    }

}