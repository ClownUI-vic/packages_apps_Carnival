/*
 * Copyright (C) 2019-2024 The Evolution X Project
 * Copyright (C) 2021-2024 The ClownUI Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.clown.settings;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

@SearchIndexable
public class ClownSettings extends DashboardFragment {

    private static final String TAG = "ClownSettings";

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.clown_settings;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.CLOWN;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.clown_settings);
}
