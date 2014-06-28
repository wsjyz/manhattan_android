package com.ivan.android.manhattanenglish.app.utils;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.remote.user.User;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-7
 * Time: PM2:42
 */
public class UserCache {
    private static User currentUser;
    private final static String USER_CACHE = "USER_CACHE";
    private final static String LOGIN_NAME = "LOGIN_NAME";
    private final static String PASSWORD = "PASSWORD";

    public static void setCurrentUser(User user) {
        currentUser = user;
        PreferencesUtil.putString(USER_CACHE, JSON.toJSONString(user));
    }

    public static User getCurrentUser() {
        if (currentUser == null) {
            String userJson = PreferencesUtil.getString(USER_CACHE, USER_CACHE);
            if (!USER_CACHE.equals(userJson)) {
                currentUser = JSON.parseObject(userJson, User.class);
            }
        }
        return currentUser;
    }

    public static String getUserId() {
        return "t2";
//        return getCurrentUser() == null ? null : getCurrentUser().getUserId();
    }

    public static void clearUserCache() {
        currentUser = null;
        PreferencesUtil.remove(USER_CACHE);
    }

    public static void setLoginName(String tel) {
        PreferencesUtil.putString(LOGIN_NAME, tel);
    }

    public static void setPassword(String psw) {
        PreferencesUtil.putString(PASSWORD, psw);
    }

    public static String getLoginName() {
        return PreferencesUtil.getString(LOGIN_NAME, "");
    }

    public static String getPassword() {
        return PreferencesUtil.getString(PASSWORD, "");
    }
}
