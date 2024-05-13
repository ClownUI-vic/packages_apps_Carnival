/*
 * Copyright (C) 2019-2024 The Evolution X Project
 * Copyright (C) 2021-2024 The ClownUI Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.clown.settings.fragments.themes;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.internal.util.clown.Utils;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.search.SearchIndexable;

import java.util.List;

import com.clown.settings.utils.DeviceUtils;

@SearchIndexable
public class Themes extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "Themes";

    private static final String KEY_ICONS_CATEGORY = "themes_icons_category";
    private static final String KEY_SIGNAL_ICON = "android.theme.customization.signal_icon";
    private static final String KEY_ANIMATIONS_CATEGORY = "themes_animations_category";
    private static final String KEY_UDFPS_ANIMATION = "udfps_animation";

    private PreferenceCategory mIconsCategory;
    private Preference mSignalIcon;
    private PreferenceCategory mAnimationsCategory;
    private Preference mUdfpsAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.clown_settings_themes);

        final Context context = getContext();
        final ContentResolver resolver = context.getContentResolver();
        final PreferenceScreen prefScreen = getPreferenceScreen();
        final Resources resources = context.getResources();

        mIconsCategory = (PreferenceCategory) findPreference(KEY_ICONS_CATEGORY);
        mSignalIcon = (Preference) findPreference(KEY_SIGNAL_ICON);
        mAnimationsCategory = (PreferenceCategory) findPreference(KEY_ANIMATIONS_CATEGORY);
        mUdfpsAnimation = (Preference) findPreference(KEY_UDFPS_ANIMATION);

        if (!DeviceUtils.deviceSupportsMobileData(context)) {
            mIconsCategory.removePreference(mSignalIcon);
        }

        FingerprintManager fingerprintManager = (FingerprintManager)
                getActivity().getSystemService(Context.FINGERPRINT_SERVICE);

        if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
            mAnimationsCategory.removePreference(mUdfpsAnimation);
        } else {
            if (!Utils.isPackageInstalled(context, "com.clown.udfps.animations")) {
                mAnimationsCategory.removePreference(mUdfpsAnimation);
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final Context context = getContext();
        final ContentResolver resolver = context.getContentResolver();
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.CLOWN;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider(R.xml.clown_settings_themes) {

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                final Resources resources = context.getResources();

                FingerprintManager fingerprintManager = (FingerprintManager)
                        context.getSystemService(Context.FINGERPRINT_SERVICE);

                if (!DeviceUtils.deviceSupportsMobileData(context)) {
                    keys.add(KEY_SIGNAL_ICON);
                }

                if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
                    keys.add(KEY_UDFPS_ANIMATION);
                } else {
                    if (!Utils.isPackageInstalled(context, "com.clown.udfps.animations")) {
                        keys.add(KEY_UDFPS_ANIMATION);
                    }
                }
                return keys;
            }
        };
}
