/*
 * Copyright (C) 2019-2024 The Evolution X Project
 * Copyright (C) 2021-2024 The ClownUI Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.clown.settings;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

public class TopLevelClownSettingsPreferenceController extends BasePreferenceController {

    public TopLevelClownSettingsPreferenceController(Context context,
            String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
}
