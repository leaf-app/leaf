package ru.dzgeorgy.auth.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.dzgeorgy.core.account.AccountUtils
import ru.dzgeorgy.core.network.Network

interface NetworkService {

    @GET("users.get")
    suspend fun get(
        @Query("user_ids") userId: Int,
        @Query("access_token") token: String,
        @Query("fields") fields: String = "photo_max, status"
    ): Network.Response<AccountUtils.AccountInfo>

}