package ru.dzgeorgy.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.friends.databinding.FragmentFriendsBinding
import ru.dzgeorgy.ui.LeafFragment

@AndroidEntryPoint
class FriendsFragment : LeafFragment() {

    override val fabIcon: Int
        get() = R.drawable.ic_search

    override val onFabClick: (fab: FloatingActionButton) -> Unit
        get() = {
            Toast.makeText(requireContext(), "I'm alive!!!", Toast.LENGTH_SHORT).show()
        }

    override val fabAlignment: Int
        get() = BottomAppBar.FAB_ALIGNMENT_MODE_END

    override val menu: Int
        get() = R.menu.friends_menu

    override val onMenuClick: (menuItem: MenuItem) -> Boolean
        get() = {
            println(it.title)
            false
        }

    private lateinit var binding: FragmentFriendsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

}