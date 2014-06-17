package com.ivan.android.manhattanenglish.app.remote.user;

import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-4
 * Time: PM1:39
 */
public class LoginServiceImpl extends AbstractService implements LoginService {

    @Override
    public User login(String tel, String password) {
        String action = "/user/login";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", tel);
        params.put("password", password);
        return postForObject(User.class, getUrl(action), params);
    }

    @Override
    public String getAuthCode(String tel) {
        String action = "/user/getAuthCode";
        Map<String, String> params = new HashMap<String, String>();
        params.put("tel", tel);
        return postForObject(String.class, getUrl(action), params);
    }

    @Override
    public Boolean register(String tel, String password, String authCode, String type) {
        String action = "/user/register";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", tel);
        params.put("password", password);
        params.put("authCode", authCode);
        params.put("type", type);

        return postForObject(Boolean.class, getUrl(action), params);
    }

    @Override
    public Boolean resetPassword(String tel, String newPassword, String authCode) {
        String action = "/user/resetPassword";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", tel);
        params.put("newPassword", newPassword);
        params.put("authCode", authCode);

        return postForObject(Boolean.class, getUrl(action), params);
    }
}
