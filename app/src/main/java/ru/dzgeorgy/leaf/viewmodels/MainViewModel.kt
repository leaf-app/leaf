package ru.dzgeorgy.leaf.viewmodels

import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import ru.dzgeorgy.core.account.AccountUtils
import ru.dzgeorgy.core.utils.ui.ILeafFragment

class MainViewModel @ViewModelInject constructor(
    private val accountUtils: AccountUtils
) : ViewModel() {

    private val _userData = MutableLiveData<AccountUtils.AccountInfo>()
    val userData: LiveData<AccountUtils.AccountInfo>
        get() = _userData

    private val _onFabClick = MutableLiveData<((FloatingActionButton) -> Unit)?>()
    val onFabClick: LiveData<((FloatingActionButton) -> Unit)?>
        get() = _onFabClick

    private val _fabIcon = MutableLiveData<@androidx.annotation.DrawableRes Int?>()
    val fabIcon: LiveData<Int?>
        get() = _fabIcon

    private val _fabAlignment =
        MutableLiveData<@BottomAppBar.FabAlignmentMode Int>()
    val fabAlignment: LiveData<Int>
        get() = _fabAlignment

    private val _menu = MutableLiveData<@androidx.annotation.MenuRes Int?>()
    val menu: LiveData<Int?>
        get() = _menu

    private val _menuOnClick = MutableLiveData<((MenuItem) -> Boolean)?>()
    val menuOnClick: LiveData<((MenuItem) -> Boolean)?>
        get() = _menuOnClick

    init {
        viewModelScope.launch {
            _userData.value = accountUtils.getActive()!!
        }
    }

    fun initFragmentInteractions(
        onFabClick: (FloatingActionButton) -> Unit,
        @DrawableRes fabIcon: Int,
        @BottomAppBar.FabAlignmentMode fabAlignment: Int,
        @MenuRes menu: Int,
        onMenuClick: (MenuItem) -> Boolean
    ) {
        _onFabClick.value = onFabClick
        _fabIcon.value = fabIcon
        _fabAlignment.value = fabAlignment
        _menu.value = menu
        _menuOnClick.value = onMenuClick
    }

    inner class FragmentCallbacks : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            super.onFragmentStarted(fm, f)
            if (f is ILeafFragment) {
                _onFabClick.value = f.onFabClick
                _fabIcon.value = f.fabIcon
                _fabAlignment.value = f.fabAlignment
                _menu.value = f.menu
                _menuOnClick.value = f.onMenuClick
            }
        }
    }

}