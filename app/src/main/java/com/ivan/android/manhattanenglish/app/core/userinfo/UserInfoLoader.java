package com.ivan.android.manhattanenglish.app.core.userinfo;

import android.content.Context;
import android.util.Log;

import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-14
 * Time: PM9:05
 */
public class UserInfoLoader extends CommonDataLoader<User> {

    public UserInfoLoader(Context context) {
        super(context);
    }

    @Override
    public User loadInBackground() {
        UserService userService = ServiceFactory.getService(UserService.class);
        User user = null;
        try {
            user = userService.loadUser(UserCache.getUserId());
            if (user != null) {
                UserCache.setCurrentUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}