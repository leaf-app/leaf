package ru.dzgeorgy.core.utils.ui

import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface ILeafFragment {

    val onFabClick: (fab: FloatingActionButton) -> Unit

    val fabIcon: Int
        @DrawableRes get

    val fabAlignment: Int
        @BottomAppBar.FabAlignmentMode get

    val menu: Int?
        @MenuRes get

    val onMenuClick: (menuItem: MenuItem) -> Boolean

}