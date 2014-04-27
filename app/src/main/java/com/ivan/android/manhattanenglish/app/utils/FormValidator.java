package com.ivan.android.manhattanenglish.app.utils;

import java.util.regex.Pattern;

/**
 * @author: Ivan Vigoss
 * Date: 14-4-27
 * Time: PM4:28
 */
public class FormValidator {
    public static Pattern mobileNumberPattern = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");

    public static boolean isMobileNumber(String number) {
        return mobileNumberPattern.matcher(number).matches();
    }

    public static boolean isStringNotBlank(String string) {
        return string != null && "".equals(string);
    }
}
