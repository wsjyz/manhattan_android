package com.ivan.android.manhattanenglish.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ivan.android.manhattanenglish.app.core.MainApplication;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-24
 * Time: AM10:39
 */
public class PreferencesUtil {

    private static SharedPreferences sp = MainApplication.GLOBAL_CONTEXT.getSharedPreferences("global", Context.MODE_PRIVATE);

    public static void putString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public static void putBoolean(String key, Boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public static void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public static void remove(String key) {
        sp.edit().remove(key).commit();
    }

    public static void clear() {
        sp.edit().clear().commit();
    }
}
