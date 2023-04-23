package geekbarains.material.view.fragments.settings

import android.os.Bundle
import android.util.Log
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import geekbarains.material.R

class SettingsFragment : PreferenceFragmentCompat() {

    companion object{
        const val TAG = "33333"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_setting, rootKey)

        findPreference<ListPreference>("ListColor")
            ?.setOnPreferenceChangeListener { _, newValue ->
                val  oldTheme = PreferenceManager.getDefaultSharedPreferences(requireActivity())
                    .getString("ListColor", "1")
                if (newValue != oldTheme){
                    Log.d( TAG, "1 SettingsFragment ListPreferenceTheme newValue = $newValue" +
                            " oldTheme = $oldTheme ")
                    requireActivity().recreate()
                }else{
                    Log.d(TAG, "2 SettingsFragment ListPreferenceTheme newValue = $newValue" +
                            " oldTheme = $oldTheme ")
                }
                true
            }


        findPreference<CheckBoxPreference>("cbScreen")
            ?.setOnPreferenceChangeListener { _, newValue ->
                val  oldValue = PreferenceManager.getDefaultSharedPreferences(requireActivity())
                    .getBoolean("cbScreen", true)
                if (newValue != oldValue){
                    Log.d(TAG, "1 SettingsFragment CheckBoxPreference newValue = $newValue")
                    requireActivity().recreate()
                }else{
                    Log.d(TAG, "2 SettingsFragment CheckBoxPreference newValue = $newValue")
                }
                true
            }
    }
}