package com.ivan.android.manhattanenglish.app.remote.user;

/**
 * @author: Ivan Vigoss
 * Date: 14-4-28
 * Time: PM8:16
 */
public interface LoginService {

    User login(String tel, String password);

    String getAuthCode(String tel);

    void register(String tel, String password, String authCode, String type);

    void resetPassword(String tel, String newPassword, String authCode);

}
