package com.ivan.android.manhattanenglish.app.core.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.utils.FormValidator;

/**
 * @author: Ivan Vigoss
 * Date: 14-4-27
 * Time: PM4:09
 */
public abstract class RegisterFragment extends Fragment {

    protected static final String TAG = "register";

    protected EditText mTelView;

    protected EditText mPasswordView;

    protected EditText mAuthCodeView;

    protected Button mAuthCodeBtn;

    protected Button mRegisterBtn;

    protected RegisterListener mCallback;

    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (RegisterListener) activity;
            mContext = activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "activity must implement RegisterListener", e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentView(), container, false);

        mTelView = (EditText) rootView.findViewById(R.id.user_name);
        mPasswordView = (EditText) rootView.findViewById(R.id.password);
        mAuthCodeView = (EditText) rootView.findViewById(R.id.auth_code);

        mAuthCodeBtn = (Button) rootView.findViewById(R.id.auth_code_button);

        mRegisterBtn = (Button) rootView.findViewById(R.id.register_button);

        init(rootView);

        return rootView;
    }

    public abstract int getContentView();

    public void init(View rootView) {

    }

    public String getInputTel() {
        return mTelView.getText().toString();
    }

    public String getInputPassword() {
        return mPasswordView.getText().toString();
    }

    public String getInputAuthCode() {
        return mAuthCodeView.getText().toString();
    }


    protected boolean checkTel() {
        String tel = mTelView.getText().toString();
        mTelView.setError(null);

        boolean valid = false;
        if (TextUtils.isEmpty(tel)) {
            mTelView.setError(mContext.getString(R.string.error_tel_required));
        } else if (!FormValidator.isMobileNumber(tel)) {
            mTelView.setError(mContext.getString(R.string.error_invalid_tel));
        } else {
            valid = true;
        }

        return valid;
    }

    protected boolean checkPassword() {
        String psw = mPasswordView.getText().toString();

        boolean valid = false;
        if (TextUtils.isEmpty(psw)) {
            mPasswordView.setError(mContext.getString(R.string.error_password_required));
        } else if (psw.length() < 6) {
            mPasswordView.setError(mContext.getString(R.string.error_invalid_password));
        } else {
            valid = true;
        }
        return valid;
    }

    protected boolean checkAuthCode() {
        String authCode = mAuthCodeView.getText().toString();

        boolean valid = false;
        if (TextUtils.isEmpty(authCode)) {
            mAuthCodeView.setError(mContext.getString(R.string.error_auth_code_required));
        } else {
            valid = true;
        }
        return valid;
    }


    public static interface RegisterListener {

        void sendAuthCode(String tel);

        void register(String tel, String password, String authCode, String userType);

    }


}