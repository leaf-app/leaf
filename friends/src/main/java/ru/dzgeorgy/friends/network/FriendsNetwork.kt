package ru.dzgeorgy.friends.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.dzgeorgy.core.network.Network

interface FriendsNetwork {

    @GET("friends.get")
    suspend fun get(
        @Query("user_id") userId: Int,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
        @Query("fields") fields: String = "photo_max",
        @Query("order") order: String = "name"
    ): Network.ResponseArray<FriendItem>

}