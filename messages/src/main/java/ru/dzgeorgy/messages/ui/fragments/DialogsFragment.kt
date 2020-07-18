package ru.dzgeorgy.messages.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.dzgeorgy.messages.R
import ru.dzgeorgy.messages.databinding.FragmentDialogsBinding
import ru.dzgeorgy.ui.ILeafFragment

class DialogsFragment : Fragment(), ILeafFragment {

    private lateinit var binding: FragmentDialogsBinding
    override val onFabClick: (fab: FloatingActionButton) -> Unit
        get() = {}
    override val fabIcon: Int
        get() = R.drawable.ic_message
    override val fabAlignment: Int
        get() = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
    override val menu: Int
        get() = R.menu.messages_menu
    override val onMenuClick: (menuItem: MenuItem) -> Boolean
        get() = { true }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogsBinding.inflate(inflater, container, false)
        return binding.root
    }

}