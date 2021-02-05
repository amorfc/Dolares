package com.example.dolares.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.dolares.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_pref, rootKey)
    }

}