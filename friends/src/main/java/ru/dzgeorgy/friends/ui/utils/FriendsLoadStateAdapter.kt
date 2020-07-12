package ru.dzgeorgy.friends.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.dzgeorgy.friends.R
import ru.dzgeorgy.friends.databinding.FooterItemBinding

class FriendsLoadStateAdapter : LoadStateAdapter<FriendsLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.footer_item, parent, false)
    ) {
        private val binding = FooterItemBinding.bind(itemView)
        private val progressBar = binding.progress
        private val errorMsg = binding.txt
        private val retry = binding.btn

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }
            progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        return holder.bind(loadState)
    }

}