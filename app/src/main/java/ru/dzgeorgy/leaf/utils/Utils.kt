package ru.dzgeorgy.leaf.utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.dzgeorgy.core.utils.ApplicationInfo
import ru.dzgeorgy.leaf.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object Utils {

    @Provides
    @Singleton
    fun getApplicationInfo() = ApplicationInfo(
        BuildConfig.VERSION_NAME,
        BuildConfig.BUILD_TYPE
    )

}