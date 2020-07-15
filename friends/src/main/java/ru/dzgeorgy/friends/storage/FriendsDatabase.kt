package ru.dzgeorgy.friends.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dzgeorgy.friends.network.FriendItem

@Database(entities = [FriendItem::class], version = 1, exportSchema = true)
abstract class FriendsDatabase : RoomDatabase() {

    abstract fun dao(): FriendDao

}