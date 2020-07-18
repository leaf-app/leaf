package ru.dzgeorgy.friends.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.dzgeorgy.friends.FriendsViewModel
import ru.dzgeorgy.friends.R
import ru.dzgeorgy.friends.databinding.FragmentFriendsBinding
import ru.dzgeorgy.friends.ui.utils.FriendsAdapter
import ru.dzgeorgy.friends.ui.utils.FriendsComparator
import ru.dzgeorgy.friends.ui.utils.FriendsLoadStateAdapter
import ru.dzgeorgy.ui.ILeafFragment

@AndroidEntryPoint
class FriendsFragment : Fragment(), ILeafFragment {

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
    private val viewModel by viewModels<FriendsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val pagingAdapter = FriendsAdapter(FriendsComparator)
//        pagingAdapter.withLoadStateHeaderAndFooter(
//            FriendsLoadStateAdapter(),
//            FriendsLoadStateAdapter()
//        )
        binding.rv.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            FriendsLoadStateAdapter(),
            FriendsLoadStateAdapter()
        )
        lifecycleScope.launch {
            viewModel.flow.collectLatest { data ->
//                pagingAdapter.loadStateFlow.collectLatest { loadStates ->
//                    println("Refresh: ${loadStates.refresh}")
//                    binding.progress.isVisible = loadStates.refresh is LoadState.Loading
//                }
                pagingAdapter.submitData(data)
            }
        }
    }

}