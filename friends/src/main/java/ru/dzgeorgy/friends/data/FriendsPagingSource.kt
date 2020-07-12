package ru.dzgeorgy.friends.data

import androidx.paging.PagingSource
import ru.dzgeorgy.core.account.AccountUtils
import ru.dzgeorgy.core.network.Network
import ru.dzgeorgy.friends.network.FriendItem
import ru.dzgeorgy.friends.network.FriendsNetwork
import javax.inject.Inject

class FriendsPagingSource @Inject constructor(
    private val accountUtils: AccountUtils,
    private val network: Network
) : PagingSource<Int, FriendItem>() {

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FriendItem> {
        return try {
            //Friends offset (0 if first loading)
            val offset = params.key ?: 0
            val response = network.createService<FriendsNetwork>().get(
                accountUtils.getActive()?.id
                    ?: throw IllegalArgumentException("Can't receive user's account"),
                offset,
                params.loadSize,
                accountUtils.getToken()
                    ?: throw IllegalArgumentException("Can't receive user's token")
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