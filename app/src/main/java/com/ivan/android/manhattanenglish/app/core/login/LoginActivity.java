package com.ivan.android.manhattanenglish.app.core.login;

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
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.home.StudentHomeActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.LoginService;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.utils.PreferencesUtil;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

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

    private Button mLoginButton;

    private boolean autoLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autoLogin = PreferencesUtil.getBoolean(AUTO_LOGIN_KEY, false);

        titleBar = (TitleBar) findViewById(R.id.login_title_bar);
        titleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(RegisterActivity.class);
            }
        });

        mTelView = (EditText) findViewById(R.id.user_name);
        mTelView.setText(UserCache.getLoginName());

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setText(UserCache.getPassword());

        //init check box
        mAutoLoginCheckbox = (CheckBox) findViewById(R.id.auto_login);
        mAutoLoginCheckbox.setChecked(autoLogin);
        mAutoLoginCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoLogin = isChecked;
                PreferencesUtil.putBoolean(AUTO_LOGIN_KEY, isChecked);
            }
        });

        //init forget psw
        mForgetPsw = (TextView) findViewById(R.id.forget_psw);
        mForgetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(FindPasswordActivity.class);
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

        if (autoLogin) {
            attemptLogin();
        }

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
            mTelView.setError(getString(R.string.error_tel_required));
            focusView = mTelView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mAuthTask = new UserLoginTask(tel, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, User> {

        private final String mTel;
        private final String mPassword;
        private LoginService loginService;

        UserLoginTask(String tel, String password) {
            mTel = tel;
            mPassword = password;
            loginService = ServiceFactory.getService(LoginService.class);
        }

        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected User doInBackground(Void... params) {
            User user = null;
            try {
                user = loginService.login(mTel, mPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(final User user) {
            mAuthTask = null;
            hideLoadingDialog();
            if (user != null) {
                UserCache.setCurrentUser(user);
                UserCache.setLoginName(mTel);
                UserCache.setPassword(mPassword);

                finish();
                if (User.USER_TYPE_TEACHER.equals(user.getType())) {
                    navigate(TeacherActivity.class);
                } else {
                    navigate(StudentHomeActivity.class);
                }
            } else {
                Toast.makeText(LoginActivity.this, R.string.error_incorrect_login_info, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}



