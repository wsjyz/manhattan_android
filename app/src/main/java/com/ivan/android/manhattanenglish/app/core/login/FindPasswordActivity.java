package com.ivan.android.manhattanenglish.app.core.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.user.LoginService;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.ivan.android.manhattanenglish.app.utils.FormValidator;

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
                    }.execute(tel);
                }
            }
        });

        mCompleteBtn = (Button) findViewById(R.id.find_psw_button);
        mCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel() && checkPassword() && checkAuthCode()) {
                    String tel = mTelView.getText().toString();
                    String psw = mPasswordView.getText().toString();
                    String authCode = mAuthCodeView.getText().toString();

                    new CommonAsyncTask<String, Void, Void>(FindPasswordActivity.this) {
                        @Override
                        protected Void getResultInBackground(String... params) {
                            String tel = params[0];
                            String psw = params[1];
                            String authCode = params[2];
                            loginService.resetPassword(tel, psw, authCode);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast.makeText(FindPasswordActivity.this, R.string.password_reset_success, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.execute(tel, psw, authCode);
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
        } else if (psw.length() < 8) {
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
