package ru.dzgeorgy.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation

@BindingAdapter("image")
fun setImage(iv: ImageView, url: String) = iv.load(url) {
    crossfade(true)
    transformations(CircleCropTransformation())
}