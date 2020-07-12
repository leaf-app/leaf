package ru.dzgeorgy.friends

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import ru.dzgeorgy.friends.data.FriendsPagingSource

class FriendsViewModel @ViewModelInject constructor(
    private val pagingSource: FriendsPagingSource,
    preferences: SharedPreferences
) :
    ViewModel() {

    private val initialLoadSize = preferences.getInt("friends_initial_pack", 30)
    private val pageSize = preferences.getInt("friends_pack_size", 20)

    val flow =
        Pager(
            PagingConfig(
                pageSize = initialLoadSize,
                initialLoadSize = pageSize
            )
        ) { pagingSource }.flow.cachedIn(
            viewModelScope
        )

}