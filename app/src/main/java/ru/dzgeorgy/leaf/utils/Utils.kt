package ru.dzgeorgy.leaf.utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.dzgeorgy.core.utils.ApplicationInfo
import ru.dzgeorgy.leaf.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Utils {

    @Provides
    @Singleton
    fun getApplicationInfo() = ApplicationInfo(
        BuildConfig.VERSION_NAME,
        BuildConfig.BUILD_TYPE
    )

}