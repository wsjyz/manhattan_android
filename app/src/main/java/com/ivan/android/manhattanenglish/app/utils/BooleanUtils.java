package com.ivan.android.manhattanenglish.app.utils;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-17
 * Time: PM10:59
 */
public class BooleanUtils {

    public static boolean toBoolean(Boolean boolValue, boolean defVal) {
        return boolValue == null ? defVal : boolValue;
    }

    public static boolean toBoolean(Boolean boolValue) {
        return toBoolean(boolValue, false);
    }
}
