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
}
