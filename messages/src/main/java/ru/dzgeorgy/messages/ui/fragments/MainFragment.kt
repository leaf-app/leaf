package ru.dzgeorgy.messages.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dzgeorgy.messages.databinding.FragmentDialogsBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentDialogsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogsBinding.inflate(inflater, container, false)
        return binding.root
    }

}