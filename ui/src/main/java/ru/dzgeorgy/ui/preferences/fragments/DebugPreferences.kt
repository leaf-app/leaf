package ru.dzgeorgy.ui.preferences.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.dzgeorgy.ui.R

class DebugPreferences : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.debug_preferences, rootKey)
    }

}