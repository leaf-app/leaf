package ru.dzgeorgy.friends.ui.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import ru.dzgeorgy.friends.R
import ru.dzgeorgy.friends.network.FriendItem

class FriendsAdapter(diffCallback: DiffUtil.ItemCallback<FriendItem>) :
    PagingDataAdapter<FriendItem, FriendsAdapter.FriendViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FriendViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(friend: FriendItem?) {
            friend?.let {
                val photoView = view.findViewById<ImageView>(R.id.photo)
                val nameView = view.findViewById<TextView>(R.id.name)
                photoView.load(it.photo) {
                    transformations(CircleCropTransformation())
                    crossfade(true)
                }
                nameView.text = "${it.firstName} ${it.lastName}"
            }
        }
    }

}

object FriendsComparator : DiffUtil.ItemCallback<FriendItem>() {
    override fun areItemsTheSame(oldItem: FriendItem, newItem: FriendItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FriendItem, newItem: FriendItem): Boolean {
        return oldItem == newItem
    }

}