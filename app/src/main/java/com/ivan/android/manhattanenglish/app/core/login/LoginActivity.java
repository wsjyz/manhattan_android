package com.ivan.android.manhattanenglish.app.core.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    public final static String LOGIN_SETTING_NAME = "login_setting";
    public final static String AUTO_LOGIN_KEY = "auto_login";
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mTelView;
    private EditText mPasswordView;
    private CheckBox mAutoLoginCheckbox;

    private TextView mForgetPsw;

    private TitleBar mTitleBar;

    private Button mLoginButton;

    private boolean autoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autoLogin = getSharedPreferences(LOGIN_SETTING_NAME, MODE_PRIVATE).getBoolean(AUTO_LOGIN_KEY, false);

        mTitleBar = (TitleBar) findViewById(R.id.login_title_bar);
        mTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });


        mTelView = (EditText) findViewById(R.id.user_name);
        mPasswordView = (EditText) findViewById(R.id.password);

        //init check box
        mAutoLoginCheckbox = (CheckBox) findViewById(R.id.auto_login);
        mAutoLoginCheckbox.setChecked(autoLogin);
        mAutoLoginCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoLogin = isChecked;
                SharedPreferences.Editor editor = getSharedPreferences(LOGIN_SETTING_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(AUTO_LOGIN_KEY, isChecked).commit();
            }
        });

        //init forget psw
        mForgetPsw = (TextView) findViewById(R.id.forget_psw);
        mForgetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findPassword = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(findPassword);
            }
        });

        //init login
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }


    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mTelView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String tel = mTelView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid tel
        if (TextUtils.isEmpty(tel)) {
            mTelView.setError(getString(R.string.error_field_required));
            focusView = mTelView;
            cancel = true;
        } else if (!isEmailValid(tel)) {
            mTelView.setError(getString(R.string.error_invalid_email));
            focusView = mTelView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(tel, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mTel;
        private final String mPassword;

        UserLoginTask(String tel, String password) {
            mTel = tel;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}



