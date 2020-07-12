package ru.dzgeorgy.core.preferences.ui.fragments

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.core.BuildConfig
import ru.dzgeorgy.core.R
import ru.dzgeorgy.core.utils.ApplicationInfo
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var applicationInfo: ApplicationInfo

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val versionPref = findPreference<Preference>("pref_version")
        val vkApiPref = findPreference<Preference>("pref_vk_api")
        val buildTypePref = findPreference<Preference>("pref_build_type")
        val debugPreferences = findPreference<Preference>("pref_group_debug")

        //Set prefs data programmatically
        if (this::applicationInfo.isInitialized) {
            versionPref?.summary = applicationInfo.version
            buildTypePref?.summary = applicationInfo.type
        }
        vkApiPref?.summary = BuildConfig.VK_API_VERSION

        //Set click listeners
        debugPreferences?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.fromMainToDebug)
            true
        }
    }

}