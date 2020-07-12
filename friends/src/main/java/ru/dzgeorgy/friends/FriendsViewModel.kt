package ru.dzgeorgy.friends

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import ru.dzgeorgy.friends.data.FriendsPagingSource

class FriendsViewModel @ViewModelInject constructor(private val pagingSource: FriendsPagingSource) :
    ViewModel() {

    val flow =
        Pager(PagingConfig(pageSize = 20, initialLoadSize = 30)) { pagingSource }.flow.cachedIn(
            viewModelScope
        )

}