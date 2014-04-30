package com.ivan.android.manhattanenglish.app.remote.login;

/**
 * @author: Ivan Vigoss
 * Date: 14-4-28
 * Time: PM8:16
 */
public interface LoginService {

    void login(String tel, String password);

    void sendSms(String tel);

    void register(String tel, String password, String authCode, String type);

    void resetPassword(String tel, String newPassword, String authCode);

}
