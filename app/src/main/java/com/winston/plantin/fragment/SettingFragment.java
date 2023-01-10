package com.winston.plantin.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.winston.plantin.R;
import com.winston.plantin.utility.Session;

public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.setting_preferences, rootKey);
        SwitchPreferenceCompat themePreference = findPreference(getString(R.string.key_theme));
        Session.getInstance().loadTheme(this.getActivity());

        if (Session.getInstance().isNight(this.getActivity())) {
            themePreference.setIcon(R.drawable.ic_baseline_mode_night_24);
            themePreference.setTitle("Night");
        } else {
            themePreference.setIcon(R.drawable.ic_baseline_light_mode_24);
            themePreference.setTitle("Day");
        }

        // toggle theme
        if (themePreference != null) {
            themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isNight = (boolean) newValue;
                if (isNight) {
                    preference.setIcon(R.drawable.ic_baseline_mode_night_24);
                    preference.setTitle("Night");
                } else {
                    preference.setIcon(R.drawable.ic_baseline_light_mode_24);
                    preference.setTitle("Day");
                }
                Session.getInstance().changeTheme(isNight);
                return true;
            });
        }
    }
}
