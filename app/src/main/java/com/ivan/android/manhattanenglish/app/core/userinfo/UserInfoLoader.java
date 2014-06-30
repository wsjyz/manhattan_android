package com.ivan.android.manhattanenglish.app.core.userinfo;

import android.content.Context;

import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-14
 * Time: PM9:05
 */
public class UserInfoLoader extends CommonDataLoader<User> {

    private String userId;

    public UserInfoLoader(Context context, String userId) {
        super(context);
        this.userId = userId;
    }

    @Override
    public User loadInBackground() {
        UserService userService = ServiceFactory.getService(UserService.class);
        User user = null;
        try {
            user = userService.loadUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}