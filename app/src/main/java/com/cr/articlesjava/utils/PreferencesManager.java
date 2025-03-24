package com.cr.articlesjava.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesManager {
    private static final String PREF_NAME = "NewsAppPrefs";
    private static final String KEY_GRID_MODE = "grid_mode";

    private SharedPreferences preferences;

    @Inject
    public PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Checks if the app is in grid mode
     */
    public boolean isGridMode() {
        return preferences.getBoolean(KEY_GRID_MODE, false);
    }

    /**
     * Sets the app's grid mode preference
     */
    public void setGridMode(boolean isGridMode) {
        preferences.edit().putBoolean(KEY_GRID_MODE, isGridMode).apply();
    }
}
