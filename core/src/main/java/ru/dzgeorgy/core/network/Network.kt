package ru.dzgeorgy.core.network

import android.content.Context
import com.squareup.moshi.JsonClass
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.dzgeorgy.core.BuildConfig
import ru.dzgeorgy.core.R
import ru.dzgeorgy.core.account.UserWithToken
import javax.inject.Inject

class Network @Inject internal constructor(
    @ApplicationContext context: Context,
    user: UserWithToken?
) {

    companion object {
        const val VK_API_URL = "https://api.vk.com/method/"
    }

    inline fun <reified T> createService(): T = getRetrofit().create(T::class.java)

    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(VK_API_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor {
            val oldRequest = it.request()
            val modifiedUrl = oldRequest.url
                .newBuilder()
                .addQueryParameter("v", BuildConfig.VK_API_VERSION)
                .addQueryParameter("lang", context.getString(R.string.locale))
            user?.token?.let { token ->
                modifiedUrl.addQueryParameter("access_token", token)
            }
            println(modifiedUrl)
            val newRequest = oldRequest.newBuilder()
                .url(modifiedUrl.build())
                .build()
            it.proceed(newRequest)
        }
        .build()

    @JsonClass(generateAdapter = true)
    data class Response<T>(
        val response: List<T>
    )

    @JsonClass(generateAdapter = true)
    data class ResponseArray<T>(
        val response: Data<T>
    )

    @JsonClass(generateAdapter = true)
    data class Data<T>(
        val count: Int,
        val items: List<T>
    )

}