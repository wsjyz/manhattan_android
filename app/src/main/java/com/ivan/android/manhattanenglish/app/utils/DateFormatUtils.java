package com.ivan.android.manhattanenglish.app.utils;

import com.ivan.android.manhattanenglish.app.core.MainApplication;

import java.text.DateFormat;
import java.util.Calendar;
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

    public static String getWeekday(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "星期日";
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            default:
                return "";
        }
    }
}
