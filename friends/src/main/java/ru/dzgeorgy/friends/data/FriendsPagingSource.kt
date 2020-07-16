package ru.dzgeorgy.friends.data

import android.content.SharedPreferences
import androidx.paging.PagingSource
import ru.dzgeorgy.core.account.User
import ru.dzgeorgy.core.network.Network
import ru.dzgeorgy.friends.network.FriendItem
import ru.dzgeorgy.friends.network.FriendsNetwork
import javax.inject.Inject

class FriendsPagingSource @Inject constructor(
    private val user: User?,
    private val network: Network,
    private val preferences: SharedPreferences
) : PagingSource<Int, FriendItem>() {

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FriendItem> {
        return try {
            //Preference which controls does friends loads by small packs or by big amount (5000 per once)
            val lazyLoad: Boolean = preferences.getBoolean("friends_paging", true)
            //Friends offset (0 if first loading)
            val offset = params.key ?: 0
            val response = network.createService<FriendsNetwork>().get(
                user!!.id,
                offset,
                if (lazyLoad) params.loadSize else 5000
            ).response
            //Creating offset for next page (null if received less friends than requested, meaning end of the list)
            val nextKey =
                if (response.items.size < params.loadSize) null else response.items.size + offset
            LoadResult.Page(
                data = response.items,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            //Temporarily throwing exception for analyze its types
            //throw e
            LoadResult.Error(e)
        }
    }

}