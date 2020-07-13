package ru.dzgeorgy.ui.preferences.fragments

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import ru.dzgeorgy.core.BuildConfig
import ru.dzgeorgy.core.utils.ApplicationInfo
import ru.dzgeorgy.core.utils.ui.ILeafFragment
import ru.dzgeorgy.ui.R
import javax.inject.Inject

@AndroidEntryPoint()
class PreferencesFragment : PreferenceFragmentCompat(), ILeafFragment {

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
        } else {
            println("Not initialized")
        }
        vkApiPref?.summary = BuildConfig.VK_API_VERSION

        //Set click listeners
        debugPreferences?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.fromMainToDebug)
            true
        }
    }

    override val onFabClick: (fab: FloatingActionButton) -> Unit
        get() = {}
    override val fabIcon: Int
        get() = R.drawable.ic_search
    override val fabAlignment: Int
        get() = BottomAppBar.FAB_ALIGNMENT_MODE_END
    override val menu: Int?
        get() = null
    override val onMenuClick: (menuItem: MenuItem) -> Boolean
        get() = { true }

}