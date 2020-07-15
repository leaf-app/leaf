package ru.dzgeorgy.friends.storage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Storage {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): FriendsDatabase = Room.databaseBuilder(
        context,
        FriendsDatabase::class.java,
        "friends_cache.db"
    ).build()

    @Provides
    fun dao(@ApplicationContext context: Context) = getDatabase(context).dao()

}