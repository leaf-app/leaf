package ru.dzgeorgy.friends.storage

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.dzgeorgy.friends.network.FriendItem

@Dao
interface FriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(friends: List<FriendItem>)

    @Query("SELECT * FROM friends ORDER BY firstName, lastName")
    fun pagingSource(): PagingSource<Int, FriendItem>

    @Query("DELETE FROM friends")
    suspend fun clearAll()

}