/*
 * Copyright (c) 2021. Adventech <info@adventech.io>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.cryart.sabbathschool.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.cryart.sabbathschool.core.misc.SSConstants
import com.cryart.sabbathschool.core.model.AppConfig

class SSSettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.ss_settings)

        val aboutPref = findPreference<Preference>(getString(R.string.ss_settings_version_key))
        val config = arguments?.getParcelable<AppConfig>(ARG_CONFIG)
        aboutPref?.summary = config?.version
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, string: String?) {
        when (string) {
            SSConstants.SS_SETTINGS_REMINDER_ENABLED_KEY, SSConstants.SS_SETTINGS_REMINDER_TIME_KEY -> {
                val reminder = (requireActivity() as? SSSettingsActivity)?.dailyReminder
                reminder?.reSchedule()
            }
        }
    }

    companion object {
        private const val ARG_CONFIG = "key:config"

        fun newInstance(config: AppConfig): SSSettingsFragment = SSSettingsFragment().apply {
            arguments = bundleOf(ARG_CONFIG to config)
        }
    }
}
