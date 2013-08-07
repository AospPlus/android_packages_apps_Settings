/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class HardwareKeys extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String HARDWARE_KEYS_CATEGORY_BINDINGS = "hardware_keys_bindings";
    private static final String HARDWARE_KEYS_ENABLE_CUSTOM = "hardware_keys_enable_custom";
    private static final String HARDWARE_KEYS_HOME_LONG_PRESS = "hardware_keys_home_long_press";
    private static final String HARDWARE_KEYS_HOME_DOUBLE_TAP = "hardware_keys_home_double_tap";
    private static final String HARDWARE_KEYS_APP_SWITCH_PRESS = "hardware_keys_app_switch_press";
    private static final String HARDWARE_KEYS_APP_SWITCH_LONG_PRESS = "hardware_keys_app_switch_long_press";

    // Available custom actions to perform on a key press.
    // Must match values for KEY_HOME_LONG_PRESS_ACTION in:
    // frameworks/base/core/java/android/provider/Settings.java
    private static final int ACTION_NOTHING = 0;
    private static final int ACTION_MENU = 1;
    private static final int ACTION_APP_SWITCH = 2;
    private static final int ACTION_SEARCH = 3;
    private static final int ACTION_VOICE_SEARCH = 4;
    private static final int ACTION_IN_APP_SEARCH = 5;

    private CheckBoxPreference mEnableCustomBindings;
    private ListPreference mHomeLongPressAction;
    private ListPreference mHomeDoubleTapAction;
    private ListPreference mAppSwitchPressAction;
    private ListPreference mAppSwitchLongPressAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.hardware_keys);
        PreferenceScreen prefSet = getPreferenceScreen();

        mEnableCustomBindings = (CheckBoxPreference) prefSet.findPreference(
                HARDWARE_KEYS_ENABLE_CUSTOM);
        mHomeLongPressAction = (ListPreference) prefSet.findPreference(
                HARDWARE_KEYS_HOME_LONG_PRESS);
        mHomeDoubleTapAction = (ListPreference) prefSet.findPreference(
                HARDWARE_KEYS_HOME_DOUBLE_TAP);
        mAppSwitchPressAction = (ListPreference) prefSet.findPreference(
                HARDWARE_KEYS_APP_SWITCH_PRESS);
        mAppSwitchLongPressAction = (ListPreference) prefSet.findPreference(
                HARDWARE_KEYS_APP_SWITCH_LONG_PRESS);
        PreferenceCategory bindingsCategory = (PreferenceCategory) prefSet.findPreference(
                HARDWARE_KEYS_CATEGORY_BINDINGS);

            int homeLongPressAction;
            homeLongPressAction = Settings.System.getInt(getContentResolver(),
                    Settings.System.KEY_HOME_LONG_PRESS_ACTION, ACTION_NOTHING);
            mHomeLongPressAction.setValue(Integer.toString(homeLongPressAction));
            mHomeLongPressAction.setSummary(mHomeLongPressAction.getEntry());
            mHomeLongPressAction.setOnPreferenceChangeListener(this);

            int homeDoubleTapAction;
            homeDoubleTapAction = Settings.System.getInt(getContentResolver(),
                    Settings.System.KEY_HOME_DOUBLE_TAP_ACTION, ACTION_NOTHING);
            mHomeDoubleTapAction.setValue(Integer.toString(homeLongPressAction));
            mHomeDoubleTapAction.setSummary(mHomeLongPressAction.getEntry());
            mHomeDoubleTapAction.setOnPreferenceChangeListener(this);

            int appSwitchPressAction = Settings.System.getInt(getContentResolver(),
                    Settings.System.KEY_APP_SWITCH_ACTION, ACTION_MENU);
            mAppSwitchPressAction.setValue(Integer.toString(appSwitchPressAction));
            mAppSwitchPressAction.setSummary(mAppSwitchPressAction.getEntry());
            mAppSwitchPressAction.setOnPreferenceChangeListener(this);

            int appSwitchLongPressAction = Settings.System.getInt(getContentResolver(),
                    Settings.System.KEY_APP_SWITCH_LONG_PRESS_ACTION, ACTION_APP_SWITCH);
            mAppSwitchLongPressAction.setValue(Integer.toString(appSwitchLongPressAction));
            mAppSwitchLongPressAction.setSummary(mAppSwitchLongPressAction.getEntry());
            mAppSwitchLongPressAction.setOnPreferenceChangeListener(this);

        mEnableCustomBindings.setChecked((Settings.System.getInt(getActivity().
                getApplicationContext().getContentResolver(),
                Settings.System.HARDWARE_KEY_REBINDING, 0) == 1));
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mHomeLongPressAction) {
            int value = Integer.valueOf((String) newValue);
            int index = mHomeLongPressAction.findIndexOfValue((String) newValue);
            mHomeLongPressAction.setSummary(
                    mHomeLongPressAction.getEntries()[index]);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.KEY_HOME_LONG_PRESS_ACTION, value);
            return true;
        } else if (preference == mHomeDoubleTapAction) {
            int value = Integer.valueOf((String) newValue);
            int index = mHomeDoubleTapAction.findIndexOfValue((String) newValue);
            mHomeDoubleTapAction.setSummary(
                    mHomeDoubleTapAction.getEntries()[index]);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.KEY_HOME_DOUBLE_TAP_ACTION, value);
            return true;
        } else if (preference == mAppSwitchPressAction) {
            int value = Integer.valueOf((String) newValue);
            int index = mAppSwitchPressAction.findIndexOfValue((String) newValue);
            mAppSwitchPressAction.setSummary(
                    mAppSwitchPressAction.getEntries()[index]);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.KEY_APP_SWITCH_ACTION, value);
            return true;
        } else if (preference == mAppSwitchLongPressAction) {
            int value = Integer.valueOf((String) newValue);
            int index = mAppSwitchLongPressAction.findIndexOfValue((String) newValue);
            mAppSwitchLongPressAction.setSummary(
                    mAppSwitchLongPressAction.getEntries()[index]);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.KEY_APP_SWITCH_LONG_PRESS_ACTION, value);
            return true;
        }
        return false;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mEnableCustomBindings) {
            Settings.System.putInt(getContentResolver(), Settings.System.HARDWARE_KEY_REBINDING,
                    mEnableCustomBindings.isChecked() ? 1 : 0);
            return true;
        }
        return false;
    }
}
