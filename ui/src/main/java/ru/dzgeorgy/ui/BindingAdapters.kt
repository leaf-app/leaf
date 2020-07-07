package ru.dzgeorgy.ui

import android.view.MenuItem
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("image")
fun setImage(iv: ImageView, url: String) = iv.load(url) {
    crossfade(true)
    transformations(CircleCropTransformation())
}

@BindingAdapter("fab_icon")
fun setFabIcon(fab: FloatingActionButton, @DrawableRes drawableRes: Int) {
    fab.setImageResource(drawableRes)
}

@BindingAdapter("on_fab_click")
fun setOnFabAction(fab: FloatingActionButton, action: ((FloatingActionButton) -> Unit)?) {
    if (action != null) fab.setOnClickListener { action.invoke(fab) }
    else fab.setOnClickListener { }
}

@BindingAdapter(value = ["bar_menu", "on_menu_click"], requireAll = true)
fun setMenu(bar: BottomAppBar, @MenuRes menuRes: Int?, action: ((MenuItem) -> Boolean)?) {
    if (menuRes != null && action != null) {
        bar.apply {
            replaceMenu(menuRes)
            setOnMenuItemClickListener(action)
        }
    } else {
        bar.apply {
            menu.clear()
            setOnMenuItemClickListener(action)
        }
    }
}