package com.ivan.android.manhattanenglish.app.remote.login;

import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-4
 * Time: PM1:39
 */
public class LoginServiceImpl extends AbstractService implements LoginService {

    @Override
    public User login(String tel, String password) {
        User user = new User();

        user.setUserId("test");
        user.setNickName("Ivan Vigoss");
        user.setMobile("18616905120");
        user.setEmail("ivan_vigoss@qq.com");
        user.setCredits(200);
        user.setSex("Male");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 2);
        user.setVipExpiredTime(calendar.getTime());

        UserCache.currentUser = user;

        return user;
    }

    @Override
    public void sendSms(String tel) {

    }

    @Override
    public void register(String tel, String password, String authCode, String type) {

    }

    @Override
    public void resetPassword(String tel, String newPassword, String authCode) {

    }
}
