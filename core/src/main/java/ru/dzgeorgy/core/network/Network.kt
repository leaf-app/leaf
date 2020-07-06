package ru.dzgeorgy.core.network

import android.content.Context
import com.squareup.moshi.JsonClass
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.dzgeorgy.core.BuildConfig
import ru.dzgeorgy.core.R
import javax.inject.Inject

class Network @Inject constructor(@ApplicationContext context: Context) {

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
                .build()
            val newRequest = oldRequest.newBuilder()
                .url(modifiedUrl)
                .build()
            println(modifiedUrl)
            it.proceed(newRequest)
        }
        .build()

    @JsonClass(generateAdapter = true)
    data class Response<T>(
        val response: List<T>
    )


}