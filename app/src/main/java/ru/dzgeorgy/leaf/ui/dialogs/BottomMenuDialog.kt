package ru.dzgeorgy.leaf.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.leaf.databinding.DialogBottomMenuBinding
import ru.dzgeorgy.leaf.viewmodels.MainViewModel

@AndroidEntryPoint
class BottomMenuDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogBottomMenuBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogBottomMenuBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@BottomMenuDialog.viewModel
            navigationView.setupWithNavController(findNavController())
        }
        return binding.root
    }

}