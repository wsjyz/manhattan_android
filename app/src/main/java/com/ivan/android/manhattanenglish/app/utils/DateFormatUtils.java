package com.ivan.android.manhattanenglish.app.utils;

import com.ivan.android.manhattanenglish.app.core.MainApplication;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-20
 * Time: AM10:08
 */
public class DateFormatUtils {

    private static DateFormat format = android.text.format.DateFormat.getDateFormat(MainApplication.GLOBAL_CONTEXT);

    public static String format(Date date) {
        if (date == null) return null;
        return format.format(date);
    }
}
