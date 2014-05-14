package com.ivan.android.manhattanenglish.app.core.userinfo;

import android.content.Context;
import android.util.Log;

import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.user.User;

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
        //todo load user info from server
        Log.i("studentInfoActivity", "loadInBackground.");

        User user = new User();
        user.setUserId("test");
        user.setNickName("Ivan");
        user.setSex("MALE");
        user.setMobile("18616905120");
        user.setEmail("ivan.vigoss88@gmail.com");
        user.setCredits(1000);
        user.setVip(true);
        user.setVipExpiredTime(new Date());

        return user;
    }
}