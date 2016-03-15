package com.example.android.poplularmoviestage1;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * @brief Activity to display the Settings
 * Created by Saurabh on 3/14/2016.
 */
public class PreferenceNSettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{

    /**
     * @brief Handles the cretion
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.basic_setup);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.display_preferences_sort_order_key)));
    }

    /**
     * @brief Handles the change in preference
     * @param newPreference
     * @param newValue
     * @return
     */
    @Override
    public boolean onPreferenceChange(Preference newPreference, Object newValue) {

        //Get in string
        String ValueInString = newValue.toString();

        if (newPreference instanceof ListPreference) {

            ListPreference sortpreference = (ListPreference) newPreference;

            int prefIndex = sortpreference.findIndexOfValue(ValueInString);

            //Case: Match preference
            if (prefIndex >= 0) {

                newPreference.setSummary(sortpreference.getEntries()[prefIndex]);
            }
            } else {

                newPreference.setSummary(ValueInString);
            }

        return true;
    }

    /**
     * @brief Listner to change
     */
    private void bindPreferenceSummaryToValue(Preference sortPreference) {

        sortPreference.setOnPreferenceChangeListener(this);

        //Case: Change in preference
        onPreferenceChange(sortPreference,PreferenceManager.getDefaultSharedPreferences(sortPreference.getContext())
                        .getString(sortPreference.getKey(), ""));
    }

}
