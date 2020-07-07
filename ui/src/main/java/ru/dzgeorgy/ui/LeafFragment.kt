package ru.dzgeorgy.ui

import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class LeafFragment : Fragment() {

    abstract val onFabClick: (fab: FloatingActionButton) -> Unit

    abstract val fabIcon: Int
        @DrawableRes get

    abstract val fabAlignment: Int
        @BottomAppBar.FabAlignmentMode get

    abstract val menu: Int
        @MenuRes get

    abstract val onMenuClick: (menuItem: MenuItem) -> Boolean

}