package com.ivan.android.manhattanenglish.app.core.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.LoginService;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.utils.BooleanUtils;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.ivan.android.manhattanenglish.app.utils.FormValidator;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

public class FindPasswordActivity extends BaseActivity {

    private EditText mTelView;

    private EditText mPasswordView;

    private EditText mAuthCodeView;

    private Button mAuthCodeBtn;

    private Button mCompleteBtn;

    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        loginService = ServiceFactory.getService(LoginService.class);

        mTelView = (EditText) findViewById(R.id.user_name);
        mPasswordView = (EditText) findViewById(R.id.password);
        mAuthCodeView = (EditText) findViewById(R.id.auth_code);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAuthCodeBtn = (Button) findViewById(R.id.auth_code_button);
        mAuthCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel()) {
                    String tel = mTelView.getText().toString();

                    new CommonAsyncTask<String, Void, String>(FindPasswordActivity.this) {
                        @Override
                        protected String getResultInBackground(String... params) {
                            String mobile = params[0];
                            return loginService.getAuthCode(mobile);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            if (!TextUtils.isEmpty(s)) {
                                Toast.makeText(FindPasswordActivity.this, "验证码：" + s, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute(tel);
                }
            }
        });

        mCompleteBtn = (Button) findViewById(R.id.find_psw_button);
        mCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel() && checkPassword() && checkAuthCode()) {
                    final String tel = mTelView.getText().toString();
                    final String psw = mPasswordView.getText().toString();  //new password
                    final String authCode = mAuthCodeView.getText().toString();

                    new CommonAsyncTask<String, Void, Boolean>(FindPasswordActivity.this) {
                        @Override
                        protected Boolean getResultInBackground(String... params) {
                            Log.i("FindPasswordActivity", "reset newpsw " + psw + " for tel " + tel);
                            return loginService.resetPassword(tel, psw, authCode);
                        }

                        @Override
                        protected void onPostExecute(Boolean aBoolean) {
                            super.onPostExecute(aBoolean);
                            if (BooleanUtils.toBoolean(aBoolean)) {
                                UserCache.setLoginName(tel);
                                UserCache.setPassword(psw);

                                Toast.makeText(FindPasswordActivity.this, R.string.password_reset_success, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(FindPasswordActivity.this, "重置密码失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                }

            }
        });


    }

    private boolean checkTel() {
        String tel = mTelView.getText().toString();
        mTelView.setError(null);

        boolean valid = false;
        if (TextUtils.isEmpty(tel)) {
            mTelView.setError(getString(R.string.error_tel_required));
        } else if (!FormValidator.isMobileNumber(tel)) {
            mTelView.setError(getString(R.string.error_invalid_tel));
        } else {
            valid = true;
        }

        return valid;
    }

    private boolean checkPassword() {
        String psw = mPasswordView.getText().toString();

        boolean valid = false;
        if (TextUtils.isEmpty(psw)) {
            mPasswordView.setError(getString(R.string.error_password_required));
        } else if (psw.length() < 6) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
        } else {
            valid = true;
        }
        return valid;
    }

    private boolean checkAuthCode() {
        String authCode = mAuthCodeView.getText().toString();

        boolean valid = false;
        if (TextUtils.isEmpty(authCode)) {
            mAuthCodeView.setError(getString(R.string.error_auth_code_required));
        } else {
            valid = true;
        }
        return valid;
    }


}
